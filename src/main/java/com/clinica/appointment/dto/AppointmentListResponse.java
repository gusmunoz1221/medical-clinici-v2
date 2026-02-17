package com.clinica.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AppointmentListResponse(
        Long id,
        LocalDateTime date,
        String status,
        String doctorFullName,
        String patientFullName

) {}
