package com.clinica.person.service;

import com.clinica.person.dto.PersonDtoRequest;
import com.clinica.person.dto.PersonDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    PersonDtoResponse create(PersonDtoRequest request);

    PersonDtoResponse getById(Long id);

    PersonDtoResponse update(Long id, PersonDtoRequest request);

    void delete(Long id);

    // --- BÚSQUEDAS ---
    Page<PersonDtoResponse> getAll(Pageable pageable);

    Page<PersonDtoResponse> search(String filter, Pageable pageable);

    PersonDtoResponse getByDni(String dni);

    PersonDtoResponse getByEmail(String email);

    // --- PAPELERA ---
    Page<PersonDtoResponse> getAllDeleted(Pageable pageable);

    void restore(Long id);
}
