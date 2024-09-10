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

    private Long specialityId;

    @Column(name = "speciality_name")
    private String specialityName;

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetectiveSpeciality> specialties =  new ArrayList<>();
}


