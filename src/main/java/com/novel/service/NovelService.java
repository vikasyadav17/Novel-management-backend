package com.novel.service;

import com.novel.dto.NovelRequestDTO;

public interface NovelService {

    public Long addNovelIfNotExists(NovelRequestDTO novel);

    public boolean findNovelByNameorLink(String name, String link);

}
