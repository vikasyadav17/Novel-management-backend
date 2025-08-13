package com.novel.web.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.novel.web.domain.Novel;

/**
 * Repository interface for managing {@link Novel} entities.
 * Extends {@link CrudRepository} to provide basic CRUD and query operations.
 * 
 * Acts as the Data Access Layer for novel-related database interactions.
 * 
 * @author Vikas Yadav
 * @version 1.0
 * @since 2025
 */
@Repository
public interface NovelRepository extends CrudRepository<Novel, Long> {

    /**
     * finds the novel with the specified id
     * 
     * @Param id - id of the novel to search for
     * @return - novel information
     */
    @Override
    @NonNull
    Optional<Novel> findById(@NonNull Long id);

    /**
     * checks if there's a novel with the specified name in the libaray
     * 
     * @param name - name of the novel to check for
     * @return true - if novel with the specified name exists .
     * @return false - if novel with the specified name doesnt exists
     */
    boolean existsByName(String name);

    /**
     * checks if there's a novel with the specified link in the library
     * 
     * @param link - link of the novel to search for
     * @return true if novel with the specified link exists .
     * @return false if novel with the specified link doesnt exists
     */
    boolean existsByLink(String link);

    /**
     * checks if there's a novel with the specified link/name in the library
     * 
     * @param name - name of the novel to search for
     * @param link - link of the novel to search for
     * @return true if novel with the specified link/name exists .
     * @return false if novel with the specified link/name doesnt exists
     */
    boolean existsByNameOrLink(String name, String link);

    /**
     * Retreives number of novels in the library
     * 
     * @return total number of novel in the library
     */
    long count();

    /**
     * Delete the novels with given id from the library
     * 
     * @param id - id of the novel to delete
     */
    void deleteById(Long id);

    /**
     * retreival of the novels from the library using regex
     * 
     * @param name - name of the novel to look for
     * @return list of the novels containing specified keyword in their name
     */
    List<Novel> findByNameContainingIgnoreCase(String name);

    /**
     * retreival of the novels of the specified genre
     * 
     * @param genre
     * @return list of the novels with the specified genre
     */
    List<Novel> findAllByGenreIgnoreCase(String genre);

}
