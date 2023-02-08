package com.givaudan.contacts.repository.jpa;

import com.givaudan.contacts.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {}
