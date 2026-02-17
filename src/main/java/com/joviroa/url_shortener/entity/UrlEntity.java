package com.joviroa.url_shortener.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "url", indexes = { @Index(name = "INDEX_SHORT_CODE", columnList = "shortCode", unique = true) })
@Data
@NoArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq")
    @SequenceGenerator(name = "url_seq", sequenceName = "url_sequence",
        initialValue = 100000, // Começa em 100.000 devido utização do H2 e tamanho mínimo do shortCode em Base62
        allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(unique = true)
    private String shortCode;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column(nullable = false)
    private Long accessCount;

    public UrlEntity(String url) {
        this.url = url;
        this.createdAt = new Date();
        this.accessCount = 0L;
    }
}
