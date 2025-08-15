package com.novel.web.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class NovelDetails {

    @Id
    private Long iD;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private String mcName;

    @Lob
    private String tags;

    @Lob
    private String specialCharacteristicOfMc;

    private String status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addedOn;

    @LastModifiedDate
    private LocalDateTime lastUpdatedOn;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id") // FK column, also PK
    @JsonIgnore
    private Novel novel;

    public NovelDetails(String description, String mcName, String tags, String specialCharacteristicOfMc) {
        this.description = description;
        this.mcName = mcName;
        this.tags = tags;
        this.specialCharacteristicOfMc = specialCharacteristicOfMc;
        this.status = "In-Progress";
    }

}
