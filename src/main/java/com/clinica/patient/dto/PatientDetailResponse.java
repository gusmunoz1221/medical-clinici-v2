package com.clinica.patient.dto;

import lombok.Builder;

@Builder
public record PatientDetailResponse(Long id,
                                    String firstName,
                                    String lastName,
                                    String dni,
                                    String email,
                                    String phone,
                                    String address,
                                    String healthInsuranceCode,
                                    String bloodType,
                                    String allergies
) {}