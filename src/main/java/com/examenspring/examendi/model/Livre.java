package com.examenspring.examendi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "livre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "livre_id")
    private Long id;
    @Column(name = "livre_title")
    @NotNull(message = "title is required")
    @Size(min = 10, max = 50, message = "Title accepts only up to 50 characters and minimum 10 characters..")
    private String title;
    @Column(name = "livre_desc")
    @NotNull(message = "description is required")
   @Size(min = 15, max = 150, message = "Description accepts only up to 150 characters and minimum 15 characters..")
    private String description;

}
