package com.novel.web.domain;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "novelopinion", schema = "library")
public class NovelOpinion {

    @Id
    public long Id;

    @Min(0)
    @Max(5)
    public Integer rating;

    public int chaptersRead;

    public boolean favorite;

    public boolean worthToContinue;

    public String chaptersFrequency;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    public Novel novel;

    public NovelOpinion(@Min(1) @Max(10) int rating, int chaptersRead, boolean favorite, boolean worthToContinue,
            String chaptersFrequency) {
        this.rating = rating;
        this.chaptersRead = chaptersRead;
        this.favorite = favorite;
        this.worthToContinue = worthToContinue;
        this.chaptersFrequency = chaptersFrequency;
    }

}
