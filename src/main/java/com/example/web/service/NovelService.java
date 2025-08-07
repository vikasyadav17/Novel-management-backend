package com.example.web.service;

import com.example.web.dto.NovelRequestDTO;

public interface NovelService {

    public Long addNovelIfNotExists(NovelRequestDTO novel);

    public boolean findNovelByNameorLink(String name, String link);

}
