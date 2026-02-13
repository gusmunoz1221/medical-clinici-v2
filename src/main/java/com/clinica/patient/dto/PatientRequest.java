package com.clinica.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record PatientRequest(
                                @NotBlank(message = "El nombre es obligatorio")
                                String firstName,
                                @NotBlank(message = "El apellido es obligatorio")
                                String lastName,
                                @NotBlank(message = "El DNI es obligatorio")
                                @Pattern(regexp = "\\d+", message = "El DNI solo debe contener números")
                                String dni,
                                @Email(message = "Formato de email inválido")
                                String email,
                                String phone,
                                String address,
                                String healthInsuranceCode,
                                String bloodType,
                                String allergies) {
}
