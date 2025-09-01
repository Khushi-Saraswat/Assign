package com.elastic_search.assignment.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import com.elastic_search.assignment.entity.CourseDocument;

@Service
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<CourseDocument> searchCourses(
            String q,
            Integer minAge,
            Integer maxAge,
            String category,
            String type,
            Double minPrice,
            Double maxPrice,
            Instant nextSessionDate,
            String sort,
            int page,
            int size) {

        Criteria criteria = new Criteria();

        if (q != null && !q.isEmpty()) {
            Criteria textCriteria = new Criteria("title").contains(q)
                    .or(new Criteria("description").contains(q));
            criteria = criteria.and(textCriteria);
        }

        if (category != null && !category.isEmpty()) {
            criteria = criteria.and("category").is(category);
        }

        if (type != null && !type.isEmpty()) {
            criteria = criteria.and("type").is(type);
        }

        if (minAge != null || maxAge != null) {
            if (minAge != null && maxAge != null) {
                Criteria ageCriteria = new Criteria("minAge").lessThanEqual(maxAge)
                        .and(new Criteria("maxAge").greaterThanEqual(minAge));
                criteria = criteria.and(ageCriteria);
            } else if (minAge != null) {
                criteria = criteria.and("maxAge").greaterThanEqual(minAge);
            } else {
                criteria = criteria.and("minAge").lessThanEqual(maxAge);
            }
        }

        if (minPrice != null || maxPrice != null) {
            if (minPrice != null && maxPrice != null) {
                criteria = criteria.and("price").between(minPrice, maxPrice);
            } else if (minPrice != null) {
                criteria = criteria.and("price").greaterThanEqual(minPrice);
            } else {
                criteria = criteria.and("price").lessThanEqual(maxPrice);
            }
        }

        if (nextSessionDate != null) {
            criteria = criteria.and("nextSessionDate").greaterThanEqual(nextSessionDate.toString());
        }

        CriteriaQuery query = new CriteriaQuery(criteria, PageRequest.of(page, size));

        if ("priceAsc".equalsIgnoreCase(sort)) {
            query.addSort(Sort.by(Sort.Order.asc("price")));
        } else if ("priceDesc".equalsIgnoreCase(sort)) {
            query.addSort(Sort.by(Sort.Order.desc("price")));
        } else {
            query.addSort(Sort.by(Sort.Order.asc("nextSessionDate")));
        }

        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);

        return hits.get().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

}
