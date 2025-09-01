package com.elastic_search.assignment.Dto;

import java.util.List;

import com.elastic_search.assignment.entity.CourseDocument;

public class SearchResponse {
    private long totalHits;
    private List<CourseDocument> courses;

    public SearchResponse(long totalHits, List<CourseDocument> courses) {
        this.totalHits = totalHits;
        this.courses = courses;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(long totalHits) {
        this.totalHits = totalHits;
    }

    public List<CourseDocument> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDocument> courses) {
        this.courses = courses;
    }
}
