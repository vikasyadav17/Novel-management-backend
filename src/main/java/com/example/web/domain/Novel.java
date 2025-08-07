package com.example.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "novel", schema = "library", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Novel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iD;

    private String name;

    private String link;

    private String genre;

    public Novel(String name, String link, String genre) {
        this.name = name;
        this.link = link;
        this.genre = genre;
    }

}
