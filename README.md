📘 Spring Boot + Elasticsearch Demo

This project demonstrates how to integrate Spring Boot with Elasticsearch to perform indexing and searching of courses.

🚀 Features

Indexing course data into Elasticsearch

Searching with filters (category, type, age range, price range, etc.)

Sorting by session date

Pagination support

⚡ Setup Instructions

1.Clone the repository:

git clone https://github.com/Khushi-Saraswat/Assign
cd assignment

2.Start Elasticsearch (Docker recommended). Example docker-compose.yml:




📝 API Usage

Example search request:

http://localhost:8080/api/search/courses?q=Algebra&minAge=8&maxAge=12&category=Math&type=COURSE&minPrice=40&maxPrice=60&sort=nextSessionDate&page=0&size=5

Sample Response:

[
  {
    "id": "1",
    "title": "Introduction to Algebra",
    "description": "Learn the basics of algebra, including variables, equations, and functions.",
    "category": "Math",
    "type": "COURSE",
    "gradeRange": "3rd–5th",
    "minAge": 8,
    "maxAge": 11,
    "price": 49.99,
    "nextSessionDate": "2025-09-05T10:00:00Z"
  }
]

⚠️ Problems Faced

Faced red lines while using Query DSL (QueryBuilders.bool(), multiMatchQuery, termQuery, etc.) due to version mismatch between Spring Boot and Elasticsearch.

Resolved by switching to Criteria API instead of the old Query DSL approach.

📚 References

🔗 YouTube: Query DSL in Elasticsearch with Spring Boot

🔗 Medium: Exploring Elasticsearch 8 with Spring Boot 3 and Spring Data Elasticsearch 5


