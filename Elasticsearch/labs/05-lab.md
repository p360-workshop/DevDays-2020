## Lab 5 - Ingest the data through Spring Boot Application and retrieve the data from Elastic index


1. launch https://<firstname-lastname>.hue.providerdataplatform.net/

1. if terminal is not already available on bottom left window, Enable the terminal window by clicking the left top most button 

1. Edit the Configuration properties to configure the elastic index 
  -  `edit the properties in the  /src/main/resource/application.propoerlties`
  -  ` elastic.server=https://vpc-p360-workshop-es-zlorjg2hjxh6cwsmstzgqgap2u.us-east-1.es.amazonaws.com/`
  -  ` index.name=<firstname-lastname>`    
  - `./gradlew clean build`
  - `./gradlew bootJar`
  
1. Build the Spring Boot Jar
  - `./gradlew bootJar`
  
1. Run Jar 
  - `java -jar ./build/libs/elastic-dev-days-demo-0.0.1-SNAPSHOT.jar `  
  
1. To load the sample data into the elastic index, call the following end point from the service
   - `curl localhost:8080/load`

1. To query the Elastic index by a given survey number 
   - `curl localhost:8080/suvey/102`

1. To Find the responses between the ratings 1 and 10
    `curl localhost:8080/rating/1/10`

1. To find the responses by text search 
    `curl localhost:8080/text/handler`


  

  
  