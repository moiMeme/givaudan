package com.givaudan.contacts.service.contact;

import com.givaudan.contacts.service.contact.dto.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ContactService {
    ContactDTO save(ContactDTO contactDTO);
    ContactDTO update(ContactDTO contactDTO);
    Optional<ContactDTO> partialUpdate(ContactDTO contactDTO);
    Optional<ContactDTO> findOne(Long id);
    void delete(Long id);
    Page<ContactDTO> search(String query, Pageable pageable);
    Page<ContactDTO> findByCriteria(String criteria, Pageable page);
    boolean existsById(Long id);
}
