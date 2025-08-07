package com.example.web.service;

import com.example.web.domain.Novel;

public interface NovelService {

    public Long addNovelIfNotExists(Novel novel);

    public boolean findNovelByNameorLink(String name, String link);

}
