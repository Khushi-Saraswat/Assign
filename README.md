📘 Spring Boot + Elasticsearch Course Search


🚀 Overview

This project demonstrates a Spring Boot application that indexes course documents into Elasticsearch and exposes a REST API to search courses with multiple filters, pagination, and sorting.

⚡ Note: Assignment B (Autocomplete & fuzzy suggestions) is skipped.

📚 References & Learning Resources

📄 Medium Article: Exploring Elasticsearch 8 with Spring Boot

🎥 YouTube Tutorial: Elasticsearch Query DSL

🤖 ChatGPT was used to understand Elasticsearch concepts and help implement the Criteria API solution.

⚡ Problem Faced & Solution
❌ Problem

At first, I tried implementing Query DSL (QueryBuilders.boolQuery(), rangeQuery(), etc.) inspired by the YouTube tutorial.
However, due to version mismatch between Spring Boot 3.x, Spring Data Elasticsearch 5.x, and Elasticsearch 8.x, I kept running into dependency errors and incompatibility issues.

✅ Solution

I switched to Criteria API, which is supported in the latest Spring Boot 3 + Elasticsearch 8 stack.
This approach worked smoothly and allowed me to build:

🔎 Multi-field search (title + description)

📊 Range filters (age, price, date)

🎯 Exact filters (category, type)

↕ Sorting and 📄 Pagination

👉 This change made the application stable and future-proof while still fulfilling all functional requirements.

🛠 Part 1: Elasticsearch Setup

Create a docker-compose.yml to run a single-node Elasticsearch cluster:

version: '3.8'
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.14.0
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - ES_JAVA_OPTS=-Xms512m -Xmx512m
    ports:
      - 9200:9200
    volumes:
      - esdata:/usr/share/elasticsearch/data

volumes:
  esdata:
    driver: local


Start Elasticsearch:

docker compose up -d


Verify Elasticsearch is running:

curl http://localhost:9200


✅ If successful, you’ll see cluster details (name, UUID, version).

📂 Part 2: Sample Data

Location: src/main/resources/sample-courses.json

Contains 50+ course objects with fields:
id, title, description, category, type, gradeRange, minAge, maxAge, price, nextSessionDate

📥 Bulk Indexing on Startup

On application startup, courses are read from the JSON file and saved into Elasticsearch’s courses index:

elasticsearchOperations.save(courses);
System.out.println("Data inserted successfully");

📸 Screenshots

✅ Application Start
✅ Data Indexed into Elasticsearch

🔍 Part 3: Search API (Assignment A)
📌 Endpoint
GET  /api/search/search/courses

🔑 Query Parameters
Parameter	Type	Description
q	String	Search keyword in title & description
minAge	Integer	Minimum age
maxAge	Integer	Maximum age
category	String	Course category
type	String	ONE_TIME, COURSE, CLUB
minPrice	Double	Minimum price
maxPrice	Double	Maximum price
startDate	String	ISO-8601 date (filter courses on/after this date)
sort	String	upcoming (default), priceAsc, priceDesc
page	Integer	Default = 0
size	Integer	Default = 10
📖 Example API Call
GET http://localhost:8080/api/search/search/courses?q=Algebra&minAge=8&maxAge=12&category=Math&type=COURSE&minPrice=40&maxPrice=60&sort=nextSessionDate&page=0&size=5

🔎 Explanation of Parameters

q=Algebra → searches in title & description

minAge=8 & maxAge=12 → filters courses suitable for ages 8–12

category=Math → filters by category

type=COURSE → only courses of type COURSE

minPrice=40 & maxPrice=60 → price range filter

sort=nextSessionDate → sorts by upcoming date

page=0 & size=5 → first page, 5 results per page

✅ Sample Response
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
