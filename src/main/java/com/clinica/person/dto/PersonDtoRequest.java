package com.clinica.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PersonDtoRequest(@NotBlank(message = "Nombre es requerido")
                               String firstName,
                               @NotBlank(message = "Apellido es requerido")
                               String lastName,
                               @Email(message = "Email inválido")
                               @NotBlank
                               String email,
                               String phone,
                               @NotBlank(message = "Nombre es requerido")
                               String dni
) {}
