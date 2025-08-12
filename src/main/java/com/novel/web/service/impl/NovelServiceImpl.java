package com.novel.web.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;
import com.novel.web.mapper.NovelRequestMapper;
import com.novel.web.repositories.NovelRepository;
import com.novel.web.service.NovelService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelRepository novelrepo;

    @Autowired
    private NovelRequestMapper novelRequestMapper;

    @Override
    public Long getNovelsCount() {
        return novelrepo.count();
    }

    @Override
    public Long addNovelIfNotExists(NovelRequestDTO novelRequestDTO) {

        Novel novel = novelRequestMapper.toEntity(novelRequestDTO);
        novel.getNovelDetails().setNovel(novel);
        log.info("Checking if there is already a novel with either same name or link ....");
        if (novelrepo.findNovelByNameOrLink(novel.getName(), novel.getLink()).isPresent()) {
            throw new DataIntegrityViolationException("Novel already exists with name: " + novel.getName()
                    + " and link : " + novel.getLink());

        }
        Novel n = novelrepo.save(novel);

        return n.getID();

        // return -1L;
    }

    @Override
    public boolean findNovelByNameOrLink(String name, String link) {
        log.info("Looking for novel with name : " + name + " or with link  " + link);
        Optional<Novel> novel = novelrepo.findNovelByNameOrLink(name, link);
        if (novel.isPresent()) {
            log.error("oops!! Novel is already in the system");
            return true;
        }
        return false;

    }

    @Override
    public List<Novel> findNovelByName(String name) {
        log.info("Looking for novel with name : " + name);
        List<Novel> novel = novelrepo.findByNameContainingIgnoreCase(name.trim());
        return novel;
    }

    @Override
    public List<Novel> findNovelByGenre(String genre) {
        log.info("Finding all the novels with genre : " + genre);
        List<Novel> novels = novelrepo.findAllByGenreIgnoreCase(genre);
        return novels;

    }

}
