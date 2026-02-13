package com.clinica.doctor.service;

import com.clinica.doctor.dto.DoctorAdminResponse;
import com.clinica.doctor.dto.DoctorPublicResponse;
import com.clinica.doctor.dto.DoctorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    Page<DoctorPublicResponse> getAllPublic(Pageable pageable);

    DoctorPublicResponse getByIdPublic(Long id);

    Page<DoctorAdminResponse> getAllAdmin(Pageable pageable);

    DoctorAdminResponse getByIdAdmin(Long id);

    Page<DoctorAdminResponse> searchAdmin(String filter, Pageable pageable);

    DoctorAdminResponse create(DoctorRequest request);

    DoctorAdminResponse update(Long id, DoctorRequest request);

    void delete(Long id);

    Page<DoctorAdminResponse> getAllDeleted(Pageable pageable);

    void restore(Long id);
}
