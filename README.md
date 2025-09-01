Course Search Application with Spring Boot & Elasticsearch

This project is a Spring Boot application that indexes a set of sample course documents into Elasticsearch and provides REST APIs to search courses with filters, pagination, and sorting.

Features

Full-text search on course title and description

Filters for:

Category (Math, Science, Art, etc.)

Type (ONE_TIME, COURSE, CLUB)

Age range (minAge/maxAge)

Price range (minPrice/maxPrice)

Upcoming session date (nextSessionDate)

Sorting:

Default: ascending by nextSessionDate

Optional: priceAsc, priceDesc

Pagination support (page, size)


Part 1: Elasticsearch Setup
1. Docker Compose Configuration

I set up a single-node Elasticsearch cluster using Docker Compose with version 8.14.0.

1. Create a docker-compose.yml:

services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.14.0
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
      - ES_JAVA_OPTS=-Xms512m -Xmx512m
    ports:
      - "9200:9200"
      - "9300:9300"
    volumes:
      - elasticsearch-data:/usr/share/elasticsearch/data
    ulimits:
      memlock:
        soft: -1
        hard: -1
    healthcheck:
      test: ["CMD-SHELL", "curl -f http://localhost:9200"]
      interval: 10s
      timeout: 10s
      retries: 5

volumes:
  elasticsearch-data:



2. Starting Elasticsearch

Run the following command in the cmd:

docker-compose up -d


This will start Elasticsearch in detached mode.
You can check running containers with:

docker ps

3. Verify Elasticsearch

To ensure Elasticsearch is running and accessible on localhost:9200:

curl http://localhost:9200


Expected Response:

{
  "name" : "5a1273218446",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "N1o20RbmQHyRk_Yac026rQ",
  "version" : {
    "number" : "8.14.0",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "8d96bbe3bf5fed931f3119733895458eab75dca9",
    "build_date" : "2024-06-03T10:05:49.073003402Z",
    "build_snapshot" : false,
    "lucene_version" : "9.10.0",
    "minimum_wire_compatibility_version" : "7.17.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "You Know, for Search"
}

