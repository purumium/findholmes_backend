package org.detective.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DetectiveApproval {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="detective_approval_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detective_id")
    private Detective detective;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private Status status;

    @Lob
    @Column(name = "rejection_reason")
    private String rejReason; // clob

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
}
