package com.novel.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NovelRatingsRequestDto {

    public int rating;
    public boolean favorite;
    public boolean worthToContinue;

}
