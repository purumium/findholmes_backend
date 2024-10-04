package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DeletedStatus")
public class DeletedStatus {

    @Id
    @Column(name = "deleted_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle에서 IDENTITY 전략을 사용
    private Long deletedId;

    @Column(name="Reason0", nullable = false)
    private int Reason0 = 0;

    @Column(name="Reason1", nullable = false)
    private int Reason1 = 0;

    @Column(name="Reason2", nullable = false)
    private int Reason2 = 0;

    @Column(name="Reason3", nullable = false)
    private int Reason3 = 0;

    @Column(name="Reason4", nullable = false)
    private int Reason4 = 0;

    @Column(name="Reason5", nullable = false)
    private int Reason5 = 0;

    @Column(name="DeReason0", nullable = false)
    private int DeReason0 = 0;

    @Column(name="DeReason1", nullable = false)
    private int DeReason1 = 0;

    @Column(name="DeReason2", nullable = false)
    private int DeReason2 = 0;

    @Column(name="DeReason3", nullable = false)
    private int DeReason3 = 0;

    @Column(name="DeReason4", nullable = false)
    private int DeReason4 = 0;

    @Column(name="DeReason5", nullable = false)
    private int DeReason5 = 0;
}
