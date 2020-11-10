## Lab 5 - Hands on demo

1. Clone the repository
  - `git clone https://github.com/p360-workshop/DevDays-2020.git`
  - `cd p360-workshop/Elasticsearch/demo`  
  
1. Examine the code - `vi src/main/java/com/optum/devdays/es/TestController.java`
  - Code showing how to insert data into an Elasticsearch index.
  - Code showing how to query using a Spring Data Repository.
  - Code showing how to query using an `ElasticsearchRestTemplate`.
  - Code showing how to query using a `RestHighLevelClient`.

1. Build the project
  - `./gradlew clean build`
  
1. Build the Spring Boot Jar
  - `./gradlew bootJar`
  
1. Deploy to Kubernetes
  - `kubectl apply -f helm.yaml`
  
[Lab 6 - Hands on demo continued](https://github.com/p360workshop/DevDays-2020/blob/feature/es/Elasticsearch/labs/06-lab.md)