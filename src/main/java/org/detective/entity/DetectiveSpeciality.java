package org.detective.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DETECTIVES_SPECIALITIES")  // 탐정의 카테고리
public class DetectiveSpeciality {

        @Id
        @Column(name = "detective_speciality_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "detective_id", referencedColumnName = "detective_id")
        @ToString.Exclude // 순환 참조 방지
        private Detective detective;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "speciality_id", referencedColumnName = "speciality_id")
        @ToString.Exclude // 순환 참조 방지
        private Speciality speciality;
}