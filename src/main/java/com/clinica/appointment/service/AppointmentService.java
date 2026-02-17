package com.clinica.appointment.service;

import com.clinica.appointment.dto.AppointmentDetailResponse;
import com.clinica.appointment.dto.AppointmentListResponse;
import com.clinica.appointment.dto.AppointmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    AppointmentDetailResponse createAppointment(AppointmentRequest request);

    AppointmentDetailResponse updateAppointment(Long id, AppointmentRequest request);

    void cancelAppointment(Long id);

    void completeAppointment(Long id, String diagnosis);

    // --- CONSULTAS ---
    AppointmentDetailResponse getAppointmentById(Long id);
    Page<AppointmentListResponse> getAllAppointments(Pageable pageable);
    Page<AppointmentListResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable);

    Page<AppointmentListResponse> getAppointmentsByPatient(Long patientId, Pageable pageable);


    // --- ADMINISTRACION ---

    //para errores de carga no para cancelaciones normales
    void deleteAppointment(Long id);
}
