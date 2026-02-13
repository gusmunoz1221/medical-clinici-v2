package com.clinica.person.dto;

import lombok.Builder;

@Builder
public record PersonDtoResponse(Long id,
                                String firstName,
                                String lastName,
                                String email,
                                String phone,
                                String dni
) {}
