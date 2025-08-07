package com.novel.web.dto;

import com.novel.web.domain.NovelDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NovelRequestDTO {

    private String link;
    private String name;
    private String genre;
    private NovelDetails novelDetails;

}
