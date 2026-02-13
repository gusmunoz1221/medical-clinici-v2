package com.clinica.patient.service;

import com.clinica.patient.dto.PatientDetailResponse;
import com.clinica.patient.dto.PatientListResponse;
import com.clinica.patient.dto.PatientRequest;
import com.clinica.patient.entity.PatientEntity;
import com.clinica.patient.mapper.PatientMapper;
import com.clinica.patient.repository.PatientRepository;
import com.clinica.person.entity.PersonEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientServiceImp implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientDetailResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByPersonDocumentNumber(request.dni()))
            throw new IllegalArgumentException("El DNI " + request.dni() + " ya se encuentra registrado.");

        if (patientRepository.existsByPersonEmail(request.email()))
            throw new IllegalArgumentException("El email " + request.email() + " ya se encuentra registrado.");

        PatientEntity patient = patientMapper.toEntity(request);

        PatientEntity created = patientRepository.save(patient);

        return patientMapper.toDetailResponse(created);
    }

    @Override
    public PatientDetailResponse getPatientById(Long id) {
        PatientEntity patient = patientRepository.findByIdWithPerson(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));

        return patientMapper.toDetailResponse(patient);
    }

    @Override
    @Transactional
    public PatientDetailResponse updatePatient(Long id, PatientRequest request) {
        PatientEntity patient = patientRepository.findByIdWithPerson(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));

        PersonEntity person = patient.getPersonEntity();

        if (!person.getDni().equals(request.dni()) && patientRepository.existsByPersonDocumentNumber(request.dni()))
            throw new IllegalArgumentException("El DNI " + request.dni() + " ya pertenece a otro paciente.");

        if (!person.getEmail().equals(request.email()) && patientRepository.existsByPersonEmail(request.email()))
            throw new IllegalArgumentException("El Email " + request.email() + " ya pertenece a otro paciente.");

        patientMapper.updateEntityFromDto(request, patient);
        PatientEntity updatedPatient = patientRepository.save(patient);

        return patientMapper.toDetailResponse(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id))
            throw new EntityNotFoundException("No se puede eliminar. Paciente con ID " + id + " no encontrado.");

        patientRepository.deleteById(id);
    }

    @Override
    public Page<PatientListResponse> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toListResponse);
    }

    @Override
    public Page<PatientListResponse> searchPatientsByLastName(String lastName, Pageable pageable) {
        return patientRepository.findByPersonLastNameContainingIgnoreCase(lastName, pageable)
                .map(patientMapper::toListResponse);
    }

    @Override
    public Page<PatientListResponse> searchPatientsByName(String filter, Pageable pageable) {
        return patientRepository.findByPersonFirstNameContainingIgnoreCaseOrPersonLastNameContainingIgnoreCase(filter, filter, pageable)
                .map(patientMapper::toListResponse);
    }

    @Override
    public Page<PatientListResponse> getAllDeletedPatients(Pageable pageable) {
        return patientRepository.findAllDeleted(pageable)
                .map(patientMapper::toListResponse);
    }

    @Override
    public PatientDetailResponse getPatientByEmail(String email) {
        return patientRepository.findByPersonEmail(email)
                .map(patientMapper::toDetailResponse)
                .orElseThrow(() -> new EntityNotFoundException("Paciente con email " + email + " no encontrado."));
    }

    @Override
    public PatientDetailResponse getPatientByDni(String dni) {
        return patientRepository.findByPersonDocumentNumber(dni)
                .map(patientMapper::toDetailResponse)
                .orElseThrow(() -> new EntityNotFoundException("Paciente con DNI " + dni + " no encontrado."));
    }

    @Override
    @Transactional
    public void restorePatient(Long id) {
        patientRepository.restorePatient(id);
    }
}
