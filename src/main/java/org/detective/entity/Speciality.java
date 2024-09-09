package org.detective.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SPECIALITIES")
public class Speciality {

    @Id
    @Column(name = "speciality_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "speciality_name")
    private String speciality_name;

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetectiveSpeciality> specialties =  new ArrayList<>();
}

