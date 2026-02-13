package com.clinica.doctor.dto;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record DoctorPublicResponse(Long id,
                                   String fullName,
                                   String specialty,
                                   BigDecimal consultationPrice
) {}
