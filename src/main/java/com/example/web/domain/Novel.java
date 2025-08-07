package com.example.web.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

    @OneToOne(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NovelDetails novelDetails;

    public Novel(String name, String link, String genre) {
        this.name = name;
        this.link = link;
        this.genre = genre;
    }

}
