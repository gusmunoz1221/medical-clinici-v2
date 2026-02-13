package com.clinica.patient.mapper;

import com.clinica.patient.dto.PatientDetailResponse;
import com.clinica.patient.dto.PatientListResponse;
import com.clinica.patient.dto.PatientRequest;
import com.clinica.patient.entity.PatientEntity;
import com.clinica.person.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public PatientEntity toEntity(PatientRequest dto) {
        PersonEntity person = PersonEntity.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .dni(dto.dni())
                .email(dto.email())
                .phone(dto.phone())
                .address(dto.address())
                .build();

        return PatientEntity.builder()
                .healthInsuranceCode(dto.healthInsuranceCode())
                .bloodType(dto.bloodType())
                .allergies(dto.allergies())
                .personEntity(person)
                .build();
    }

    public PatientListResponse toListResponse(PatientEntity entity) {
        if (entity == null) return null;
        PersonEntity person = entity.getPersonEntity();

        return PatientListResponse.builder()
                .id(entity.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dni(person.getDni())
                .email(person.getEmail())
                .build();
    }

    public PatientDetailResponse toDetailResponse(PatientEntity entity) {
        if (entity == null) return null;
        PersonEntity person = entity.getPersonEntity();

        return PatientDetailResponse.builder()
                .id(entity.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dni(person.getDni())
                .email(person.getEmail())
                .phone(person.getPhone())
                .address(person.getAddress())
                .healthInsuranceCode(entity.getHealthInsuranceCode())
                .bloodType(entity.getBloodType())
                .allergies(entity.getAllergies()) // Agregado
                .build();
    }

    public void updateEntityFromDto(PatientRequest request, PatientEntity patient) {
        if (request == null || patient == null) return;
        PersonEntity person = patient.getPersonEntity();

        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setDni(request.dni());
        person.setEmail(request.email());
        person.setPhone(request.phone());
        person.setAddress(request.address());
        patient.setHealthInsuranceCode(request.healthInsuranceCode());
        patient.setBloodType(request.bloodType());
        patient.setAllergies(request.allergies());
    }
}
