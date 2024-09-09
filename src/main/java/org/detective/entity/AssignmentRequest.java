//package org.detective.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Data
//@Entity
//@Table(name = "AssignmentRequests",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"request_id", "detective_id"}))
//public class AssignmentRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "request_id", referencedColumnName = "request_id",  nullable = false)
//    private Request request;
//
//    @ManyToOne
//    @JoinColumn(name = "detective_id", referencedColumnName = "detective_id", nullable = false)
//    private Detective detective;
//
//    @ManyToOne
//    @JoinColumn(name = "specialty_id", referencedColumnName = "specialty_id", nullable = false)
//    private Specialty speciality;
//
//    @Column(name="assigned_time", updatable = false)
//    private LocalDate assignedAt = LocalDate.now();
//
//    @Column(name="reply_time")
//    private LocalDate replyAt;
//
//    @Column(name="request_status")
//    private String requestStatus;
//}
