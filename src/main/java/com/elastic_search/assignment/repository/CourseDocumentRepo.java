package com.elastic_search.assignment.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elastic_search.assignment.entity.CourseDocument;

@Repository
public interface CourseDocumentRepo extends ElasticsearchRepository<CourseDocument, String> {

}
