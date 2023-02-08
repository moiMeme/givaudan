package com.givaudan.contacts.service.contact.mapper;

import com.givaudan.contacts.domain.Contact;
import com.givaudan.contacts.service.contact.dto.ContactDTO;
import com.givaudan.contacts.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {}
