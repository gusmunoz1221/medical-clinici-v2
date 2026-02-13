package com.clinica.doctor.mapper;

import com.clinica.doctor.dto.DoctorAdminResponse;
import com.clinica.doctor.dto.DoctorPublicResponse;
import com.clinica.doctor.dto.DoctorRequest;
import com.clinica.doctor.entity.DoctorEntity;
import com.clinica.person.dto.PersonDtoResponse;
import com.clinica.person.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public DoctorEntity toEntity(DoctorRequest request) {
        PersonEntity personEntity = PersonEntity.builder()
                .firstName(request.person().firstName())
                .lastName(request.person().lastName())
                .email(request.person().email())
                .phone(request.person().phone())
                .build();

        return DoctorEntity.builder()
                .specialty(request.specialty())
                .consultationPrice(request.consultationPrice())
                .person(personEntity) // Asignamos la relación
                .build();
    }


    public DoctorPublicResponse toPublicResponse(DoctorEntity entity) {
        if (entity == null) return null;

        String fullName = String.format("%s %s",
                entity.getPerson().getFirstName(),
                entity.getPerson().getLastName());

        return DoctorPublicResponse.builder()
                .id(entity.getId())
                .fullName(fullName)
                .specialty(entity.getSpecialty())
                .consultationPrice(entity.getConsultationPrice())
                .build();
    }

    public DoctorAdminResponse toAdminResponse(DoctorEntity entity) {
        if (entity == null) return null;

        return DoctorAdminResponse.builder()
                .id(entity.getId())
                .specialty(entity.getSpecialty())
                .consultationPrice(entity.getConsultationPrice())
                .personDetails(toPersonDtoResponse(entity.getPerson()))
                .build();
    }

    private PersonDtoResponse toPersonDtoResponse(PersonEntity person) {
        if (person == null) return null;

        return PersonDtoResponse.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phone(person.getPhone())
                .build();
    }

    public void updateEntity(DoctorEntity entity, DoctorRequest request) {
        entity.setSpecialty(request.specialty());
        entity.setConsultationPrice(request.consultationPrice());

        PersonEntity person = entity.getPerson();
        if (person != null) {
            person.setFirstName(request.person().firstName());
            person.setLastName(request.person().lastName());
            person.setEmail(request.person().email());
            person.setPhone(request.person().phone());
        }
    }
}
