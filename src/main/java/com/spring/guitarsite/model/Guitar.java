package com.spring.guitarsite.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "guitars")
@Entity
public class Guitar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private GuitarType type;

    @Column(nullable = false)
    private String brand;

    @Column
    private String model;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "guitar_images", joinColumns = @JoinColumn(name = "guitar_id"))
    @Column(name = "image_url")
    private List<String> additionalImages = new ArrayList<>();

    @Column(name = "body_material")
    private String bodyMaterial;

    @Column(name = "neck_material")
    private String neckMaterial;

    @Column(name = "fingerboard_material")
    private String fingerboardMaterial;

    @Column(name = "number_of_frets")
    private Integer frets;

    @Column(name = "scale_length")
    private Double scaleLength;

    @Column(name = "color")
    private String color;

    @Column(name = "country_of_origin")
    private String country;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_id")
    private User addedBy;

    @OneToMany(mappedBy = "guitar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}

