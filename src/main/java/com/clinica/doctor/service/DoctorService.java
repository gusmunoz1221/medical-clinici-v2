package com.clinica.doctor.service;

import com.clinica.doctor.dto.DoctorAdminResponse;
import com.clinica.doctor.dto.DoctorPublicResponse;
import com.clinica.doctor.dto.DoctorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    Page<DoctorPublicResponse> getAllPublicDoctors(Pageable pageable);

    DoctorPublicResponse getByIdPublicDoctor(Long id);

    Page<DoctorAdminResponse> getAllAdminDoctors(Pageable pageable);

    DoctorAdminResponse getByIdAdminDoctor(Long id);

    Page<DoctorAdminResponse> searchAdminDoctors(String filter, Pageable pageable);

    DoctorAdminResponse createDoctor(DoctorRequest request);

    DoctorAdminResponse updateDoctor(Long id, DoctorRequest request);

    Page<DoctorAdminResponse> getAllDeletedDoctor(Pageable pageable);

    void deleteDoctor(Long id);
    void restoreDoctor(Long id);

}
