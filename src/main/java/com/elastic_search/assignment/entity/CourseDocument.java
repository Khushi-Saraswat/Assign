package com.elastic_search.assignment.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "courses")
public class CourseDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Field(type = FieldType.Keyword, name = "category")
    private String category;

    @Field(type = FieldType.Keyword, name = "type")
    private String type;

    @Field(type = FieldType.Keyword, name = "gradeRange")
    private String gradeRange;

    @Field(type = FieldType.Integer, name = "minAge")
    private Integer minAge;

    @Field(type = FieldType.Integer, name = "maxAge")
    private Integer maxAge;

    @Field(type = FieldType.Double, name = "price")
    private Double price;

    @Field(type = FieldType.Date, name = "nextSessionDate")
    private Instant nextSessionDate;

    @CompletionField(maxInputLength = 100)
    private String[] suggest;
}
