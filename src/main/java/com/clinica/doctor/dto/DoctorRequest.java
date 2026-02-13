package com.clinica.doctor.dto;

import com.clinica.person.dto.PersonDtoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record DoctorRequest(@NotBlank(message = "La especialidad es requerida")
                            String specialty,

                            @NotNull(message = "El precio es requerido")
                            @DecimalMin(value = "0.0", inclusive = false)
                            BigDecimal consultationPrice,

                            @NotNull(message = "Datos de la persona requeridos")
                            @Valid
                            PersonDtoRequest person

) {}
