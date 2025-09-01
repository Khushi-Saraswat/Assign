package com.elastic_search.assignment.Service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.elastic_search.assignment.entity.CourseDocument;
import com.elastic_search.assignment.repository.CourseDocumentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Startuplistener implements CommandLineRunner {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseDocumentRepo courseDocumentRepo;

    @Override
    public void run(String... args) throws Exception {
        List<CourseDocument> courses = null;

        if (courseDocumentRepo.count() == 0) {

            try {
                ClassPathResource resource = new ClassPathResource("sample-courses.json");
                InputStream stream = resource.getInputStream();
                courses = objectMapper.readValue(stream, objectMapper.getTypeFactory().constructCollectionType(
                        java.util.List.class, com.elastic_search.assignment.entity.CourseDocument.class));
                courseDocumentRepo.saveAll(courses);
                System.out.println("Data inserted successfully");
            } catch (Exception e) {
                System.err.println("Data is not Found: ");
            }
        } else {
            System.out.println("Data is already present in the index");
        }

    }

}
