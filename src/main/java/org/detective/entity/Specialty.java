package org.detective.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Specialty {

    @Id
    @Column(name = "specialty_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialty_name")
    private String name;

    @OneToMany(mappedBy = "specialty",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetectiveSpecialty> specialties =  new ArrayList<>();
}
