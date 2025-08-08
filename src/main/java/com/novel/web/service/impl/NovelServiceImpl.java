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

@Service
@Primary
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelRepository novelrepo;

    @Autowired
    private NovelRequestMapper novelRequestMapper;

    @Override
    public Long addNovelIfNotExists(NovelRequestDTO novelRequestDTO) {
        try {
            System.out.println("Adding Novel");
            Novel novel = novelRequestMapper.toEntity(novelRequestDTO);
            novel.getNovelDetails().setNovel(novel);
            System.out.println("Novel to String -> " + novel.toString());
            if (novelrepo.findByNameOrLink(novel.getName(), novel.getLink()).isPresent()) {
                throw new DataIntegrityViolationException("Novel already exists with name: " + novel.getName()
                        + " /  with the link : " + novel.getLink());

            }
            Novel n = novelrepo.save(novel);

            return n.getID();

        } catch (Exception e) {
            System.err.println("Exception occurred while saving novel : " + e.getMessage());
            e.printStackTrace(); // Optional: for full stack trace
        }
        return -1L;
    }

    @Override
    public boolean findNovelByNameorLink(String name, String link) {
        Optional<Novel> novel = novelrepo.findByNameOrLink(name, link);
        if (novel.isPresent()) {
            System.out.println("oops!! Novel is already in the system");
            return true;
        }
        return false;

    }

    @Override
    public Novel findNovelByName(String name) {
        Optional<Novel> novel = novelrepo.findByNameIgnoreCase(name.trim());
        Novel n = null;
        if (novel.isPresent())
            n = novel.get();

        return n;
    }

    @Override
    public List<Novel> findNovelByGenre(String genre) {
        System.out.println("Finding all the novels with genre : " + genre);
        List<Novel> novels = novelrepo.findAllByGenreIgnoreCase(genre);
        return novels;

    }

}
