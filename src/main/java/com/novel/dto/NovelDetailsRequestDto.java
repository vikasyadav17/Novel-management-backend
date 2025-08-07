package com.novel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NovelDetailsRequestDto {

    private String description;

    private boolean mcCheating;

    private String specialCharacteristicOfMc;

}
