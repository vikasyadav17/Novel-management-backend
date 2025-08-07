package com.example.web.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.web.domain.Novel;
import com.example.web.repositories.NovelRepository;
import com.example.web.service.NovelService;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelRepository novelrepo;

    @Override
    public Long addNovelIfNotExists(Novel novel) {
        try {
            System.out.println("Adding Novel");
            if (novelrepo.findByName(novel.getName()).isPresent()) {
                throw new DataIntegrityViolationException("Novel already exists with name: " + novel.getName());
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
    public boolean findNovelByName(String name) {
        Optional<Novel> novel = novelrepo.findByName(name);
        if (novel.isPresent()) {
            System.out.println("oops!! Novel is already in the system");
            return true;
        }
        return false;

    }

}
