package com.clinica.patient.dto;

import lombok.Builder;

@Builder
public record PatientListResponse(Long id,
                                  String firstName,
                                  String lastName,
                                  String dni,
                                  String email
) {}