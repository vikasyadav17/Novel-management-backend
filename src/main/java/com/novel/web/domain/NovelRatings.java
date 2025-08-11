package com.novel.web.domain;

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
@Table(name = "novelratings", schema = "library")
public class NovelRatings {

    @Id
    public long Id;

    @Min(1)
    @Max(10)
    public int rating;

    public boolean favorite;

    public boolean worthToContinue;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    public Novel novel;

    public NovelRatings(int rating, boolean favorite, boolean worthToContinue) {
        this.rating = rating;
        this.favorite = favorite;
        this.worthToContinue = worthToContinue;
    }

}
