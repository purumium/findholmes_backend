package org.detective.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
import lombok.*;
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

<<<<<<< HEAD
@Entity
@Getter
@Setter
=======
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REVIEWS")
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
public class Review {

    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
    @JoinColumn(name="detective_id")
    private Detective detective;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

    private int rating;

    @Lob
=======
    @JoinColumn(name="detective_id", referencedColumnName = "detective_id")
    private Detective detective;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", referencedColumnName = "client_id")
    private Client client;

    @Column(name="rating")
    private int rating;

    @Lob
    @Column(name="content")
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    private String content; // clob

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
