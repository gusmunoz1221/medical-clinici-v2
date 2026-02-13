package com.clinica.doctor.dto;

import com.clinica.person.dto.PersonDtoResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DoctorAdminResponse(Long id,
                                  String specialty,
                                  BigDecimal consultationPrice,
                                  PersonDtoResponse personDetails

) {}
