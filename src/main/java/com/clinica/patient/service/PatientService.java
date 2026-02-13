package com.clinica.patient.service;

import com.clinica.patient.dto.PatientDetailResponse;
import com.clinica.patient.dto.PatientListResponse;
import com.clinica.patient.dto.PatientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientDetailResponse createPatient(PatientRequest request);

    PatientDetailResponse getPatientById(Long id);

    PatientDetailResponse updatePatient(Long id, PatientRequest request);

    PatientDetailResponse getPatientByEmail(String email);

    PatientDetailResponse getPatientByDni(String dni);

    Page<PatientListResponse> getAllPatients(Pageable pageable);

    Page<PatientListResponse> searchPatientsByLastName(String lastName, Pageable pageable);

    Page<PatientListResponse> searchPatientsByName(String filter, Pageable pageable);

    Page<PatientListResponse> getAllDeletedPatients(Pageable pageable);

    void deletePatient(Long id);
    void restorePatient(Long id);
}
