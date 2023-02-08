package com.givaudan.contacts.service.upload;

import com.givaudan.contacts.domain.Contact;
import com.givaudan.contacts.repository.jpa.ContactRepository;
import com.givaudan.contacts.repository.search.ContactSearchRepository;
import com.givaudan.contacts.web.rest.errors.ImportExportException;
import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadDownloadContactService {

    public static List<String> TYPES = List.of("text/csv", "application/vnd.ms-excel");
    private final ContactSearchRepository contactSearchRepository;
    private final ContactRepository contactRepository;

    public void exportCSV(Writer writerCsv) {
        StatefulBeanToCsv<Contact> writer =
                new StatefulBeanToCsvBuilder<Contact>
                        (writerCsv)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(';')
                        .withOrderedResults(false).build();

        Iterable<Contact> contacts = contactSearchRepository.findAll();
        try {
            writer.write(contacts.iterator());
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new ImportExportException();
        }
    }

    public void importCSV(Reader reader) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();

        CsvToBean<Contact> cb = new CsvToBeanBuilder<Contact>(csvReader)
                .withType(Contact.class)
                .build();
        List<Contact> contacts = cb.parse();
        contactRepository.saveAll(contacts);
        contactSearchRepository.saveAll(contacts);
    }

    public boolean hasCSVFormat(MultipartFile file) {
        return Optional.ofNullable(file)
                .map(MultipartFile::getContentType)
                .map(contentType -> CollectionUtils.contains(TYPES.listIterator(), contentType))
                .orElse(false);
    }

    public ByteArrayInputStream toCSV() {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                OutputStreamWriter out = new OutputStreamWriter(byteArrayOutputStream)) {
            exportCSV(out);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }


}
