package com.clinica.appointment.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull(message = "La fecha de la cita es obligatoria")
        @Future(message = "La cita debe ser en el futuro")
        LocalDateTime date,
        @NotNull(message = "El ID del doctor es obligatorio")
        Long doctorId,
        @NotNull(message = "El ID del paciente es obligatorio")
        Long patientId,
        String reason
) {}
