package com.clinica.appointment.dto;

import java.time.LocalDateTime;

public record AppointmentDetailResponse(
        Long id,

        LocalDateTime date,
        String status,
        String reason,
        String diagnosis,
        Long doctorId,
        String doctorName,
        String doctorSpecialty,
        Long patientId,
        String patientName,
        String patientDni,
        LocalDateTime createdAt
) {}
