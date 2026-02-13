package com.clinica.person.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons", indexes = {
        @Index(name = "idx_person_dni", columnList = "dni", unique = true),
        @Index(name = "idx_person_email", columnList = "email", unique = true)
})
@Entity
@SoftDelete(columnName = "is_deleted")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 20, unique = true)
    private String dni;
    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 20)
    private String phone;
    @Column(length = 150)
    private String address;

    // AUDITORIA AUTOMATICA
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
