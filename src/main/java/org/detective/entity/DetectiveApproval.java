package org.detective.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DETECTIVE_APPROVALS")
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
public class DetectiveApproval {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="detective_approval_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
    @JoinColumn(name = "detective_id")
=======
    @JoinColumn(name = "detective_id", referencedColumnName = "detective_id")
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    private Detective detective;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
<<<<<<< HEAD
    private Status status;
=======
    private ApprovalStatus approvalStatus;
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc

    @Lob
    @Column(name = "rejection_reason")
    private String rejReason; // clob

<<<<<<< HEAD
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
}
=======
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
}
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
