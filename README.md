

---

# 📘 Spring Boot + Elasticsearch Demo

This project demonstrates how to integrate **Spring Boot** with **Elasticsearch** to perform indexing and searching of courses.

---

## 🚀 Features
- Indexing course data into Elasticsearch
-  multiple filters, pagination, and sorting 
- Sorting by **session date**
- Pagination support


---

## 🚧 Problem I Faced

While implementing search functionality, I initially tried using **Elasticsearch Query DSL** directly with `QueryBuilders` (`boolQuery`, `multiMatchQuery`, `rangeQuery`, etc.).

However, I faced issues like **red lines in Vs Code** because of:

* Version mismatches between **Spring Boot 3**, **Spring Data Elasticsearch 5**, and **Elasticsearch 8**
* Deprecated or removed methods in the latest dependencies

👉 To resolve this, I switched to **Spring Data Criteria API** for building dynamic queries, which worked smoothly and was fully compatible with my setup.

---

## 📚 References

* 🔗 **YouTube**: [Query DSL in Elasticsearch with Spring Boot](https://www.youtube.com/watch?v=lqLCNV-Bpbo&utm_source=chatgpt.com)
* 🔗 **Medium**: [Exploring Elasticsearch 8 with Spring Boot 3 and Spring Data Elasticsearch 5](https://medium.com/@truongbui95/exploring-elasticsearch-8-utilizing-spring-boot-3-and-spring-data-elasticsearch-5-495650115197)
* 🤖 **ChatGPT**: Used for guidance and troubleshooting during setup and query implementation

---

## ⚡ Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/Khushi-Saraswat/Assign
cd assignment
```

### 2. Elasticsearch Setup

a) Create a `docker-compose.yml` to run a single-node Elasticsearch cluster
b) Start Elasticsearch:

```bash
docker compose up -d
```

c) Verify Elasticsearch is running:

```bash
curl http://localhost:9200
```

✅ If successful, you’ll see cluster details (name, UUID, version).

---

## 📝 API Usage

### Example Search Request

```http
http://localhost:8080/api/search/search/courses?q=Algebra&minAge=8&maxAge=12&category=Math&type=COURSE&minPrice=40&maxPrice=60&sort=nextSessionDate&page=0&size=5
```

### Sample Response

```json
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
```

---

