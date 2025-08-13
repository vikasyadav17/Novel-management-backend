package com.novel.web.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;
import com.novel.web.mapper.NovelRequestMapper;
import com.novel.web.repositories.NovelRepository;
import com.novel.web.service.NovelService;

import jakarta.persistence.EntityNotFoundException;
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
     * injecting novelRepository and novelRequestMapper into the novelServiceImpl
     * constructor
     * 
     * @param novelRepository    - repository for novel data operations
     * @param novelRequestMapper - Mapper for converting between DTO and entity
     */
    public NovelServiceImpl(NovelRepository novelRepo, NovelRequestMapper novelRequestMapper) {
        this.novelRepo = novelRepo;
        this.novelRequestMapper = novelRequestMapper;
    }

    /**
     * returns the total number of novels present in the library
     *
     */
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
        if (novelRepo.existsByNameOrLink(novel.getName(), novel.getLink())) {
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

        if (novelRepo.existsByNameOrLink(name, link)) {
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

    /**
     * updates novel information
     * 
     * @param name         - update the novel name to
     * @param link         - update the novel link to
     * @param originalName - update the novel original name to
     * @param genre        - update the novel genre name to
     * @return
     * @throws NotFoundException if there is no record with specified id in the
     *                           Library
     */
    @Override
    public Novel updateNovel(Long id, String name, String link, String originalName, String genre) {
        Optional<Novel> novelFound = novelRepo.findById(id);
        if (!novelFound.isPresent()) {
            throw new EntityNotFoundException("No Novel exists in the system with id " + id);
        }
        Novel novel = novelFound.get();

        if (name != null && !name.trim().isEmpty()) {
            log.info("Name has to updated from {}  to {}", novel.getName(), name);
            log.info(
                    "but before  updating the date . Need to check if there's already a novel with  name : {} in library",
                    name);
            if (novelRepo.existsByName(name)) {
                throw new DataIntegrityViolationException(
                        "Novel with specified name already exists in the library :- " +
                                name);
            }
            novel.setName(name);
        }
        if (link != null && !link.trim().isEmpty()) {
            log.info("Link has to updated from {}  to {}", novel.getLink(), link);

            log.info(
                    "but before we doing the update . Need to check if there's already a novel with that link in library");
            if (novelRepo.existsByLink(link)) {
                throw new DataIntegrityViolationException(
                        "Novel with specified link already exists in the library :- " +
                                link);
            }
            novel.setLink(link);
        }
        if (originalName != null && !originalName.trim().isEmpty()) {
            log.info("OriginalName has to updated from {}  to {}", novel.getOriginalName(), originalName);
            novel.setOriginalName(originalName);
        }
        if (genre != null && !genre.trim().isEmpty()) {
            log.info("Genre has to updated from {}  to {}", novel.getGenre(), genre);
            novel.setGenre(genre);
        }
        novelRepo.save(novel);
        return novel;
    }

}
