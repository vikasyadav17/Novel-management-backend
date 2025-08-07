package com.example.web.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.web.domain.Novel;

@Repository
public interface NovelRepository extends CrudRepository<Novel, Long> {

    Novel save(Novel novel);

    Optional<Novel> findById(Long id);

    Optional<Novel> findByNameOrLink(String name, String link);

    long count();

    void deleteById(Long id);

}
