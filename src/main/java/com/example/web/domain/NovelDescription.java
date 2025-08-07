package com.example.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "noveldescription", schema = "library")
public class NovelDescription {

    @Id
    private Long iD;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Novel novel;

    @Lob
    private String description;

    private int chaptersRead;

    @Lob
    private String comment;

}
