## Lab 5 - Hands on demo

1. Clone the repository
  - `git clone https://github.optum.com/bander65/elastic-dev-days-demo`
  
1. Build the project
  - `./gradlew clean build`
  
1. Build the Spring Boot Jar
  - `./gradlew bootJar`
  
1. Deploy to Kubernetes
  - `kubectl apply -f helm.yaml`
  
