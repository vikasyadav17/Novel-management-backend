package com.novel.web.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.novel.web.domain.Novel;

@Repository
public interface NovelRepository extends CrudRepository<Novel, Long> {

    @Override
    @NonNull
    Optional<Novel> findById(@NonNull Long id);

    boolean existsByName(String name);

    boolean existsByLink(String link);

    boolean existsByNameOrLink(String name, String link);

    long count();

    void deleteById(Long id);

    List<Novel> findByNameContainingIgnoreCase(String name);

    List<Novel> findAllByGenreIgnoreCase(String genre);

}
