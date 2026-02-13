package com.clinica.patient.dto;

import lombok.Builder;

@Builder
public record PatientResponse(Long id,
                              String firstName,
                              String lastName,
                              String dni,
                              String email,
                              String healthInsuranceCode,
                              String bloodType
) {}
