package com.novel.web.service;

import com.novel.web.dto.NovelRequestDTO;

public interface NovelService {

    public Long addNovelIfNotExists(NovelRequestDTO novel);

    public boolean findNovelByNameorLink(String name, String link);

}
