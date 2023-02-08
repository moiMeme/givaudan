package com.givaudan.contacts.service.contact.impl;

import com.givaudan.contacts.domain.Contact;
import com.givaudan.contacts.repository.jpa.ContactRepository;
import com.givaudan.contacts.repository.search.ContactSearchRepository;
import com.givaudan.contacts.service.contact.ContactService;
import com.givaudan.contacts.service.contact.criteria.ContactSpecificationsBuilder;
import com.givaudan.contacts.service.contact.dto.ContactDTO;
import com.givaudan.contacts.service.contact.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final ContactSearchRepository contactSearchRepository;

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        ContactDTO result = contactMapper.toDto(contact);
        contactSearchRepository.index(contact);
        return result;
    }

    @Override
    public ContactDTO update(ContactDTO contactDTO) {
        log.debug("Request to update Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        ContactDTO result = contactMapper.toDto(contact);
        contactSearchRepository.index(contact);
        return result;
    }

    @Override
    public Optional<ContactDTO> partialUpdate(ContactDTO contactDTO) {
        log.debug("Request to partially update Contact : {}", contactDTO);

        return contactRepository
            .findById(contactDTO.getId())
            .map(existingContact -> {
                contactMapper.partialUpdate(existingContact, contactDTO);

                return existingContact;
            })
            .map(contactRepository::save)
            .map(savedContact -> {
                contactSearchRepository.save(savedContact);

                return savedContact;
            })
            .map(contactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactDTO> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id).map(contactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
        contactSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Contacts for query {}", query);
        return contactSearchRepository.search(query, pageable).map(contactMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ContactDTO> findByCriteria(String criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        Specification<Contact> specification = new ContactSpecificationsBuilder().with(criteria).build();
        return Optional.ofNullable(specification)
                .map(spec -> contactRepository.findAll(spec, page))
                .orElse(contactRepository.findAll(page))
                .map(contactMapper::toDto);
    }

    @Override
    public boolean existsById(Long id) {
        return !contactRepository.existsById(id);
    }

}
