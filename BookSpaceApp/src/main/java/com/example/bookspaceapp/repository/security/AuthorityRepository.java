package com.example.bookspaceapp.repository.security;

import com.example.bookspaceapp.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
