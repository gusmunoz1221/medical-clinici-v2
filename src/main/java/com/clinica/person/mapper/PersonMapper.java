package com.clinica.person.mapper;

import com.clinica.person.dto.PersonDtoRequest;
import com.clinica.person.dto.PersonDtoResponse;
import com.clinica.person.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonEntity toEntity(PersonDtoRequest request) {
        if (request == null) return null;

        PersonEntity person = new PersonEntity();
        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setEmail(request.email());
        person.setPhone(request.phone());
        person.setDni(request.dni());

        return person;
    }

    public PersonDtoResponse toResponse(PersonEntity entity) {
        if (entity == null) return null;

        return PersonDtoResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .dni(entity.getDni())
                .build();
    }

    public void updateEntity(PersonEntity entity, PersonDtoRequest request) {
        if (entity == null || request == null) return;

        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setDni(request.dni());
    }
}