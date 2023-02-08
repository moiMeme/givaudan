package com.givaudan.contacts.web.rest;

import com.givaudan.contacts.service.upload.UploadDownloadContactService;
import com.givaudan.contacts.web.utils.HeaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/csv")
@Slf4j
@RequiredArgsConstructor
public class UploadDownloadController {

     private final UploadDownloadContactService uploadDownloadContactService;


    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        log.debug("REST request to export Contacts ");
        String filename = "contacts-List.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        uploadDownloadContactService.exportCSV(response.getWriter());
    }
    @PostMapping("/import")
    public ResponseEntity<Void> importCSV(@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("REST request to upload Contacts ");
        if (uploadDownloadContactService.hasCSVFormat(file)) {
                uploadDownloadContactService.importCSV(new InputStreamReader(file.getInputStream()));
                HttpHeaders headers = HeaderUtil.createAlert(applicationName, "file uploaded", file.getName());
                return ResponseEntity.noContent().headers(headers).build();
        }

        HttpHeaders headers = HeaderUtil.createFailureAlert(applicationName, "importCSV", "csv file not valid");

        return ResponseEntity.noContent().headers(headers).build();
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() {
        ByteArrayInputStream byteArrayInputStream = uploadDownloadContactService.toCSV();
        InputStreamResource file = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "contact.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
