package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DETECTIVE_APPROVALS")
public class DetectiveApproval {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="detective_approval_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detective_id", referencedColumnName = "detective_id")
    private Detective detective;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    @Lob
    @Column(name = "rejection_reason")
    private String rejReason; // clob

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
}