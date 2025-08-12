package com.novel.web.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;
import com.novel.web.mapper.NovelRequestMapper;
import com.novel.web.repositories.NovelRepository;
import com.novel.web.service.NovelService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of NovelService interface
 * Handles all business logic related to novel operations
 * 
 * @author Vikas yadav
 * @version 1.0
 * @since 2025
 */
@Slf4j
@Service
@Primary
public class NovelServiceImpl implements NovelService {

    private final NovelRepository novelRepo;

    private final NovelRequestMapper novelRequestMapper;

    /**
     * 
     * @param novelRepository    - repository for novel data operations
     * @param novelRequestMapper - Mapper for converting between DTO and entity
     */
    public NovelServiceImpl(NovelRepository novelRepo, NovelRequestMapper novelRequestMapper) {
        this.novelRepo = novelRepo;
        this.novelRequestMapper = novelRequestMapper;
    }

    @Override
    public Long getNovelsCount() {
        log.info("Fetching total count of novels");
        Long count = novelRepo.count();
        log.info("Total number of novels found : {}", count);
        return count;
    }

    /**
     * Adds a novel to the library if it doesnt already exists
     * Checks for duplicate novels based on name or link
     * 
     * @param NovelRequestDto - Novel data transfer object containing novel details
     * @return Id of the newly created Novel
     * @throws DataIntegrityViolationException if novel already exists with same
     *                                         name or link
     * @throws IllegalArgumentException        if novelRequestDto is null
     */
    @Override
    public Long addNovelIfNotExists(NovelRequestDTO novelRequestDTO) {
        if (novelRequestDTO == null) {
            throw new IllegalArgumentException("NovelRequestDTO cannot be null");
        }
        log.info("adding novel : {}", novelRequestDTO);
        Novel novel = novelRequestMapper.toEntity(novelRequestDTO);
        if (novel.getNovelDetails() != null) {
            novel.getNovelDetails().setNovel(novel);
        }
        log.info("Checking if novel already exists with name: {} or link: {}",
                novel.getName(), novel.getLink());
        if (novelRepo.findNovelByNameOrLink(novel.getName(), novel.getLink()).isPresent()) {
            throw new DataIntegrityViolationException("Novel already exists with name: " + novel.getName()
                    + " and link : " + novel.getLink());

        }
        Novel savedNovel = novelRepo.save(novel);
        log.info("Novel successfully added with ID: {}", savedNovel.getID());

        return savedNovel.getID();

        // return -1L;
    }

    /**
     * check if there's already a name with same name or link
     * 
     * @param name - name of the novel to search for
     * @param link - link of the novel to search for
     * @return true if there's a novel already with same name or link
     * @throws IllegalArgumentException if both name and link are null or empty
     */
    @Override
    public boolean findNovelByNameOrLink(String name, String link) {
        if ((name == null || name.trim().isEmpty()) && (link == null || link.trim().isEmpty())) {
            throw new IllegalArgumentException("Atleast one parameter (name or link ) must be provided");
        }

        log.info("Searching for novel with name: {} or link: {}", name, link);
        Optional<Novel> novel = novelRepo.findNovelByNameOrLink(name, link);
        if (novel.isPresent()) {
            log.warn("Novel already exists in the system");
            return true;
        }
        log.info("No existing novel found with given name or link");
        return false;

    }

    /**
     * Finds novels by name using case-insensitive partial matching
     * 
     * @param name - Name or partial name of the novel to search for
     * @return list of novels whose name contain the search term
     * @throws IllegalArgumentException if name is null or empty
     */
    @Override
    public List<Novel> findNovelByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or blank");
        }
        log.info("Looking for novel with name {} : ", name);
        List<Novel> novels = novelRepo.findByNameContainingIgnoreCase(name.trim());
        log.info("Found {} novel(s) with name containing: {}", novels.size(), name.trim());
        return novels;
    }

    /**
     * Find Novels by genre using case-insensitive exact matching
     * 
     * @param genre - genre of the novel to search for
     * @return List of the novels with the specified genre
     * @throws IllegalArgumentException if genre is null or empty
     */
    @Override
    public List<Novel> findNovelByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("genre cannot be null or empty");
        }
        log.info("Finding all the novels with genre : {} ", genre);
        List<Novel> novels = novelRepo.findAllByGenreIgnoreCase(genre);
        log.info("Found {} novel(s) with genre: {}", novels.size(), genre.trim());
        return novels;

    }

}
