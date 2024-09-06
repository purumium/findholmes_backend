package org.detective.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Specialties")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id")
    private Long specialtyId;

    @Column(name = "specialty_name", unique = true, nullable = false)
    private String specialtyName;

    @ManyToMany(mappedBy = "specialties")
    private Set<Detective> detectives = new HashSet<>();

    // Getters and setters
}