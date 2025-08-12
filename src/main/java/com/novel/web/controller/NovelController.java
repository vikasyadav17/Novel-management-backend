package com.novel.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;
import com.novel.web.mapper.NovelRequestMapper;
import com.novel.web.service.NovelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/novels")
public class NovelController {

    private final NovelService novelService;

    private final NovelRequestMapper novelRequestMapper;

    public NovelController(NovelService novelService, NovelRequestMapper novelRequestMapper) {
        this.novelService = novelService;
        this.novelRequestMapper = novelRequestMapper;

    }

    @Operation(summary = "Total no. of novels", description = "returns total numbers novels in the library")
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalNovels() {
        log.info("Fetching total number of novels");
        try {
            Long novelCount = novelService.getNovelsCount();
            log.info("total number of novels in the library are : {} ", novelCount);
            return ResponseEntity.ok(novelCount);
        } catch (Exception ex) {
            log.error("Error fetching novel count: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Home route", description = "Returns a welcome message for the novel library")
    @GetMapping("/home")
    public String home() {
        return "Novel library";
    }

    @Operation(summary = "Add a novel", description = "Adds a novel to the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Novel added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Novel already exists"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<String> addNovelIfNotExists(@RequestBody NovelRequestDTO novelDTO) {
        log.info("User wants to add novel : {} in the database", novelDTO.toString());

        if (novelDTO == null) {
            return ResponseEntity.badRequest().body("Novel data cannot be null");
        }
        try {

            Long id = novelService.addNovelIfNotExists(novelDTO);
            // if (id == -1L) {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Novel
            // not added due to an error.");
            // }
            return ResponseEntity.status(HttpStatus.CREATED).body("Novel added with id : " + id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Duplicate novel found: {} ", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error: {} ", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }

    }

    @Operation(summary = "searches for a novel with a name/genre", description = "returns novel details if exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retreived novels successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input - at least one search parameter required"),
            @ApiResponse(responseCode = "404", description = "No novels found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<NovelRequestDTO>> searchNovel(@RequestParam(required = false) String name,
            @RequestParam(required = false) String genre) {

        boolean hasName = name != null && !name.trim().isEmpty();
        boolean hasGenre = genre != null && !genre.trim().isEmpty();

        if (!hasName && !hasGenre) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "At least one search parameter (name or genre) must be provided");
        }
        try {
            List<Novel> novels;
            if (hasGenre) {
                log.info("Searching novels by genre: {}", genre.trim());
                novels = novelService.findNovelByGenre(genre.trim());
            } else {
                log.info("Searching novels by name: {}", name.trim());
                novels = novelService.findNovelByName(name.trim());
            }
            if (novels.isEmpty()) {
                String searchType = hasName ? "name" : "genre";
                String searchTerm = hasName ? name : genre;
                log.info("No novels found for {}: {}", searchType, searchTerm);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No novels found for " + searchType + ": " + searchTerm);
            }
            log.info("{} novel(s) found", novels.size());
            List<NovelRequestDTO> novelsDTO = novelRequestMapper.toDTOList(novels);
            return ResponseEntity.ok(novelsDTO);

        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error searching novels: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while searching novels");
        }
    }

}
