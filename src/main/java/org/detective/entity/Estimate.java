package org.detective.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ESTIMATES")
public class Estimate
{
    @Id
    @Column(name="estimate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="request_id", referencedColumnName = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="detective_id", referencedColumnName = "detective_id")
    private Detective detective;

    private String title;

    @Lob
    private String description; //clob

    private int price;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    public Estimate(Client client,Request request, Detective detective,String title, String description,int price) {
        this.client = client;
        this.request = request;
        this.detective = detective;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}