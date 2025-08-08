package com.novel.web.service;

import java.util.List;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;

public interface NovelService {

    public Long addNovelIfNotExists(NovelRequestDTO novel);

    public boolean findNovelByNameorLink(String name, String link);

    public List<Novel> findNovelByName(String name);

    public List<Novel> findNovelByGenre(String genre);

}
