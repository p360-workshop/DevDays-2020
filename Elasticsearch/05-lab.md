:toc:


# Data Ingestion through the Spring Boot Application and retrieve te data from Elastric Index


## Review the Code

launch https://<firstname-lastname>.hue.providerdataplatform.net/

if terminal is not already available on bottom left window, Enable the terminal window by clicking the left top most button 

The dependency 'spring-boot-starter-data-elasticsearch' is added  for Spring Data ElasticSearch application.

```

dependencies {
	implementation group: 'org.springframework.boot', name: 'spring-boot-starter-web'
	implementation group: 'org.springframework.boot', name: 'spring-boot-starter-data-elasticsearch'
	implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.11'
}

```
   
The sample data model, that is going to be pushed is created as below. The Elastic indecx is configured through application configurations 

``` 

@Document(indexName = "${application.index.name}")
public class Response {

    @Id
    private String responseId;
    private String surveyId;
    private Integer questionNumber;
    private Integer rating;
    private String responseText;
    private String user;

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

```

Application configuration can be configured for elastic server and elastic index

```
 
elastic.server=https://vpc-p360-workshop-es-zlorjg2hjxh6cwsmstzgqgap2u.us-east-1.es.amazonaws.com/
index.name==<firstname-lastname>

```


### Sample Data Ingestion

The responseRepository is extended from ElasticSearchRepository 

```

@Repository
public interface ResponseRepository extends ElasticsearchRepository<Response, String> {

    Page<Response> findBySurveyId(String surveyId, Pageable pageable);

}

```

The sample data is saved with responseRepository

```
       
responseRepository.saveAll(responses);
    
```

### Querying the Elastic Index

ElasticsearchRespository - High level.  Easy to use for simpler operations. 
    
``` 

@GetMapping("/survey/{surveyId}")
public List<Response> getResponsesForSurveyId(@PathVariable String surveyId) {
    PageRequest page = PageRequest.of(0, 10);
    return responseRepository.findBySurveyId(surveyId, page).toList();
}
    

```  
    
    
ElasticsearchRestTemplate - Provides range query for searching range of values.

```    

Query searchQuery = new NativeSearchQueryBuilder().withQuery(
        QueryBuilders.rangeQuery("rating")
                .gte(low)
                .lte(high)
).build();

```
    
      
# Run the Application 

## Build the Application

Get the code from  https://github.com/raghaj/elasticsearch-dem [here]

'''
  git clone https://github.com/raghaj/elasticsearch-demo
  
'''

Edit the Configuration properties to configure the elastic index 

The properties is : /src/main/resource/application.properties 

```
elastic.server=https://vpc-p360-workshop-es-zlorjg2hjxh6cwsmstzgqgap2u.us-east-1.es.amazonaws.com:443 

index.name=<firstname-lastname>     

```

if you are planning to use your local elastic stack

the properties would be 


```
elastic.server=localhost:9200 

index.name=<firstname-lastname>     

```

  
Clean build and create Boot Jar

```

./gradlew clean build 
./gradlew bootJar 

```

## Build the Spring Boot Jar

```

./gradlew bootJar 

```
  
## Run the Jar
  
Run Jar 

```
java -jar ./build/libs/elastic-dev-days-demo-0.0.1-SNAPSHOT.jar   

``` 
  
## Load the sample data 
  
To load the sample data into the elastic index, call the following end point from the service

```
curl localhost:8080/load 

If the request is successful, We should get response as 

"responses loaded!"

```

## Query the elastic index 

To query the Elastic index by a given survey number 

```
curl localhost:8080/survey/102 

if the request is successful, we should get response in the format similar to below

    {
        "responseId": "e2422ef8-dfe6-489b-90b0-293ec9acef54",
        "surveyId": "101",
        "questionNumber": 0,
        "rating": 3,
        "responseText": "trailer analytic style predatory triathlon mythology enticing fax scoreboard brace copper airliner win coveralls food programmer acting programmer",
        "user": "xxx"
    }
       

```

To Find the responses between the ratings 1 and 10

```
curl localhost:8080/rating/1/10 

if request is successful, we should get response similar to below

    {
        "responseId": "4aa2b234-5599-4438-85c4-be329c735253",
        "surveyId": "100",
        "questionNumber": 0,
        "rating": 4,
        "responseText": "handler grimace fountain style hoe handler win smear pity washing",
        "user": "steve"
    }
    

```

To find the responses by text search 

```
curl localhost:8080/text/handler 

if request is successful, we should see response similar to below

[
    {
        "responseId": "4aa2b234-5599-4438-85c4-be329c735253",
        "surveyId": "100",
        "questionNumber": 0,
        "rating": 4,
        "responseText": "handler grimace fountain style hoe handler win smear pity washing",
        "user": "steve"
    },
    {
        "responseId": "cf932ed9-cf88-4116-afe3-6ad2710ef4ce",
        "surveyId": "100",
        "questionNumber": 1,
        "rating": 7,
        "responseText": "dash embrace win programmer handler swollen",
        "user": "bruce"
    }
]    

```

## Veriy the data by querying in Kibana

1. Launch the Kibana Lab Environment 
    
    https://kibana.workshop.providerdataplatform.net
 
1. To List the Indexes

    GET /_cat/indices

2. Get the data from the Index

    GET /your_IndexName/_search



  
  