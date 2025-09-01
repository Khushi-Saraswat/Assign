Perfect! I’ll add a **Step-by-Step Setup & Run** section to your README so it’s crystal clear for anyone to follow. Here’s the updated README including this section:

---

# Spring Boot Elasticsearch Course Search

## Overview

This project demonstrates a **Spring Boot application** that indexes course documents into **Elasticsearch** and exposes a REST API to search courses with multiple filters, pagination, and sorting.

> Note: Assignment B (Autocomplete & fuzzy suggestions) is skipped.

---

## References

* Medium article explaining Elasticsearch with Spring Boot: [Exploring Elasticsearch 8 with Spring Boot](https://medium.com/@truongbui95/exploring-elasticsearch-8-utilizing-spring-boot-3-and-spring-data-elasticsearch-5-495650115197)
* YouTube tutorial on Query DSL: [Elasticsearch Query DSL](https://www.youtube.com/watch?v=BZQOFch1ejI)

  * ⚠️ Due to **version mismatch issues**, Query DSL could not be used directly. Instead, **Criteria API** was implemented for search functionality.
* ChatGPT was used to understand Elasticsearch concepts and implement the **Criteria API solution**.

---

## Part 1: Elasticsearch Setup

1. Create a `docker-compose.yml` for a single-node Elasticsearch cluster:

```yaml
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
```

2. Start Elasticsearch:

```bash
docker compose up -d
```

3. Verify Elasticsearch is running:

```bash
curl http://localhost:9200
```

---

## Part 2: Sample Data

* File: `src/main/resources/sample-courses.json`
* Contains 50+ course objects with fields:

  * `id`, `title`, `description`, `category`, `type`, `gradeRange`, `minAge`, `maxAge`, `price`, `nextSessionDate`

### Bulk Indexing

* On application startup, courses are read from JSON and saved into Elasticsearch (`courses` index) using **ElasticsearchOperations**:

```java
elasticsearchOperations.save(courses);
```

* Verify data via:

```bash
curl http://localhost:9200/courses/_search
```

---

## Part 3: Search API (Assignment A)

### Endpoint

```
GET /api/search
```

### Query Parameters

| Parameter | Type    | Description                                             |
| --------- | ------- | ------------------------------------------------------- |
| q         | String  | Search keyword in title & description (fuzzy supported) |
| minAge    | Integer | Minimum age                                             |
| maxAge    | Integer | Maximum age                                             |
| category  | String  | Course category                                         |
| type      | String  | ONE\_TIME, COURSE, CLUB                                 |
| minPrice  | Double  | Minimum price                                           |
| maxPrice  | Double  | Maximum price                                           |
| startDate | String  | ISO-8601 date (filter courses on/after this date)       |
| sort      | String  | `upcoming` (default), `priceAsc`, `priceDesc`           |
| page      | Integer | Default 0                                               |
| size      | Integer | Default 10                                              |

### Example Requests

1. Search by keyword:

```bash
curl "http://localhost:8080/api/search?q=dinors"
```

2. Filter by category, age, and price:

```bash
curl "http://localhost:8080/api/search?category=Math&minPrice=10&maxPrice=100&minAge=8&maxAge=12"
```

3. Pagination & sorting:

```bash
curl "http://localhost:8080/api/search?page=1&size=5&sort=priceDesc"
```

### Response Example

```json
{
  "total": 52,
  "courses": [
    {
      "id": "1",
      "title": "Introduction to Physics",
      "category": "Science",
      "price": 50.0,
      "nextSessionDate": "2025-09-10T10:00:00Z"
    }
  ]
}
```

---

## Notes on Criteria API

* Initially attempted **Query DSL** (YouTube reference), but version mismatches with Spring Data Elasticsearch caused errors.
* Switched to **Criteria API**, which works reliably with the current Spring Boot 3 + Elasticsearch 8 setup.
* The Criteria API supports:

  * Multi-field search (`title` + `description`)
  * Range filters (`age`, `price`, `nextSessionDate`)
  * Exact filters (`category`, `type`)
  * Sorting and pagination

---

## Step-by-Step Setup & Run

1. **Clone Repository**

```bash
git clone <your-repo-link>
cd <repo-folder>
```

2. **Start Elasticsearch**

```bash
docker compose up -d
```

3. **Verify Elasticsearch**

```bash
curl http://localhost:9200
```

* You should see cluster information returned.

4. **Run Spring Boot Application**

```bash
mvn spring-boot:run
```

* Application reads `sample-courses.json` and indexes all courses into Elasticsearch automatically.

5. **Verify Indexed Data**

```bash
curl http://localhost:9200/courses/_search
```

6. **Call Search API**

* Example:

```bash
curl "http://localhost:8080/api/search?q=Physics&minAge=10&maxAge=15&sort=priceAsc&page=0&size=5"
```

* Response will include filtered, paginated, and sorted course results.

---

This README is now **submission-ready** and clearly explains setup, indexing, and API usage with examples.

If you want, I can also **add a small section showing screenshots or commands for verifying data and API results**, which will make it look more professional for reviewers.

Do you want me to add that too?
