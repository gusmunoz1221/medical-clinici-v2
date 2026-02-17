package com.clinica.person.service;

import com.clinica.person.dto.PersonDtoRequest;
import com.clinica.person.dto.PersonDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    PersonDtoResponse createPerson(PersonDtoRequest request);

    PersonDtoResponse getPersonById(Long id);

    PersonDtoResponse updatePerson(Long id, PersonDtoRequest request);

    void deletePerson(Long id);

    // --- BÚSQUEDAS ---
    Page<PersonDtoResponse> getAll(Pageable pageable);

    Page<PersonDtoResponse> searchPerson(String filter, Pageable pageable);

    PersonDtoResponse getPersonByDni(String dni);

    PersonDtoResponse getPersonByEmail(String email);

    // --- PAPELERA ---
    Page<PersonDtoResponse> getAllDeleted(Pageable pageable);

    void restorePerson(Long id);
}
