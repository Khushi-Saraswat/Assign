package com.elastic_search.assignment.Controller;

import java.time.Instant;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elastic_search.assignment.Service.SearchService;
import com.elastic_search.assignment.entity.CourseDocument;

@RestController
@RequestMapping("/api/search")
public class CourseSearchController {

    private final SearchService searchService;

    public CourseSearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/courses")
    public List<CourseDocument> searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant nextSessionDate,
            @RequestParam(defaultValue = "nextSessionDate") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return searchService.searchCourses(
                q, minAge, maxAge, category, type, minPrice, maxPrice, nextSessionDate, sort, page, size);
    }

}
