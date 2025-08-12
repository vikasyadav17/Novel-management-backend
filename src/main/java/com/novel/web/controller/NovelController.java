package com.novel.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    private NovelService novelService;

    @Autowired
    private NovelRequestMapper novelRequestMapper;

    public NovelController(NovelService novelService) {
        this.novelService = novelService;

    }

    @Operation(summary = "Total no. of novels", description = "returns total numbers novels in the library")
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalNovels() {
        log.info("User has requested total number of libraries in the novel");
        Long novelCount = novelService.getNovelsCount();
        log.info("total number of novels in the library are  " + novelCount);
        return ResponseEntity.ok(novelCount);
    }

    @Operation(summary = "Home route", description = "Returns a welcome message for the novel library")
    @GetMapping("/home")
    public String home() {
        return "Novel library";
    }

    @Operation(summary = "adds a novel", description = "Adds a novel in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Novel added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<String> addNovelIfNotExists(@RequestBody NovelRequestDTO novelDTO) {
        log.info("User wants to add novel : " + novelDTO.toString() + " in the database");
        try {
            Long id = -1L;

            id = novelService.addNovelIfNotExists(novelDTO);
            // if (id == -1L) {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Novel
            // not added due to an error.");
            // }
            return ResponseEntity.ok("Record added with ID " + id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Duplicate novel found: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }

    }

    @Operation(summary = "searches for a novel with a name/genre", description = "returns novel details if exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Novel added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<NovelRequestDTO>> getNovelByName(@RequestParam(required = false) String name,
            @RequestParam(required = false) String genre) {
        List<Novel> novels = null;
        if (name != null && name.length() == 0 && genre != null && genre.length() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "novel name / genre must not be empty");
        if (genre != null && genre.length() != 0) {
            log.info("user has requested novel search with genre");
            novels = novelService.findNovelByGenre(genre);
        } else {
            log.info("user has requested novel search with name");
            novels = novelService.findNovelByName(name);
        }
        log.info(novels.size() + " novel/s found that contains keyword " + name);
        List<NovelRequestDTO> dto = novelRequestMapper.toDTOList(novels);
        return ResponseEntity.ok(dto);
    }

}
