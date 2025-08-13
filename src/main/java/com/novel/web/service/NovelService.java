package com.novel.web.service;

import java.util.List;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;

/**
 * Service interface for managing {@link Novel} services.
 * 
 * Contains declartion of th business logic methods related to novels.
 * This layer defines the contract for services that handle novelmanagement
 * 
 * @author Vikas Yadav
 * @version 1.0
 * @since 2025
 */
public interface NovelService {

    public Long addNovelIfNotExists(NovelRequestDTO novel);

    public boolean findNovelByNameOrLink(String name, String link);

    public List<Novel> findNovelByName(String name);

    public List<Novel> findNovelByGenre(String genre);

    public Long getNovelsCount();

    public Novel updateNovel(Long id, String name, String link, String originalName, String genre);

}
