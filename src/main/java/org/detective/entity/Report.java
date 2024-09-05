package org.detective.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private Long id;

    @Column(name="report_title")
    private String title;

    @Enumerated(EnumType.STRING)
    private ReportType category;

    @Lob
    @Column(name="report_content")
    private String content; // clob


    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Lob
    private String answer;

    @Column(name="answered_at")
    private LocalDateTime answeredAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
