package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.detective.RequestStatus;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "AssignmentRequests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"request_id", "detective_id"}))
@NoArgsConstructor
public class AssignmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_request_id")
    private Long assignmentRequestId;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "request_id",  nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name = "detective_id", referencedColumnName = "detective_id", nullable = false)
    private Detective detective;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "speciality_id", nullable = false)
    private Speciality speciality;

    @Column(name="assigned_time", updatable = false)
    private LocalDate assignedAt = LocalDate.now();

    @Column(name="reply_time")
    private LocalDate replyAt;

    @Column(name="request_status")
    private RequestStatus requestStatus = RequestStatus.WAITING;

    public AssignmentRequest(Request request, Detective detective, Speciality speciality) {
        this.request = request;
        this.detective = detective;
        this.speciality = speciality;
    }
}
