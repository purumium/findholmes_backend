package org.detective.entity;

<<<<<<< HEAD
=======
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

<<<<<<< HEAD
@Entity
@Getter
@Setter
public class Estimate {

=======
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ESTIMATES")
public class Estimate
{
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    @Id
    @Column(name="estimate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="detective_id")
    private Detective detective;

    @ColumnDefault("0")
=======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="request_id", referencedColumnName = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="detective_id", referencedColumnName = "detective_id")
    private Detective detective;

    @ColumnDefault("0")
    @Column(name="price")
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    private int price;

    @Lob
    private String description; //clob

    @Column(name="start_date")
<<<<<<< HEAD
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;
=======
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createAt;

}
