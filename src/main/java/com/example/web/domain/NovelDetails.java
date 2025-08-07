package com.example.web.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "novel")
@NoArgsConstructor
@Entity
@Table(name = "noveldetails", schema = "library")
public class NovelDetails {

    @Id
    private Long iD;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean mcCheating;

    @Lob
    private String specialCharacteristicOfMc;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id") // FK column, also PK
    private Novel novel;

    NovelDetails(String description, boolean mcCheating, String specialCharacteristicOfMc) {
        this.description = description;
        this.mcCheating = mcCheating;
        this.specialCharacteristicOfMc = specialCharacteristicOfMc;
    }

}
