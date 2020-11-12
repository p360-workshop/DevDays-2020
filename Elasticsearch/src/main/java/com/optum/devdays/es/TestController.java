package com.optum.devdays.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optum.devdays.es.entity.Response;
import com.optum.devdays.es.repository.ResponseRepository;
import org.apache.commons.lang3.RandomUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class TestController {

    private final static List<String> USERNAMES = List.of("steve", "natasha", "bruce", "scott", "wanda", "tony", "hope", "clint", "carol");
    private final static List<String> WORDS = List.of("handler", "download", "listen", "dash", "prey", "shiver", "washing", "hoe", "heck", "pen", "demographer", "seminar", "outperform", "retrospect", "eyeglass", "trailer", "brace", "ontological", "trappings", "formula", "farce", "tibetan", "food", "deflect", "clue", "tendon", "intestine", "geometric", "cul-de-sac", "star", "conjure", "tower", "grimace", "blog", "programmer", "electromagnetic", "earth", "veritable", "airliner", "opus", "swollen", "cherokee", "indy", "appoint", "quarterfinal", "future", "network", "inversion", "fluff", "civility", "clandestine", "promised", "scoreboard", "losing", "energy-efficient", "predatory", "style", "win", "seem", "headdress", "makeup", "triathlon", "fountain", "likelihood", "dashboard", "pity", "captivate", "mythology", "permission", "dog", "unfairness", "carry", "appreciate", "cost", "copper", "suede", "return", "enticing", "goblet", "embrace", "purge", "spinach", "petal", "chi", "analytic", "labeling", "coveralls", "smear", "cat", "touch", "running", "jab", "heel", "feverish", "food", "acting", "wriggle", "kickback", "arm", "fax");

    private final ElasticsearchOperations elasticsearchRestTemplate;
    private final ObjectMapper objectMapper;
    private final ResponseRepository responseRepository;
    private final RestHighLevelClient restHighLevelClient;

    public TestController(ElasticsearchOperations elasticsearchRestTemplate,
                          ObjectMapper objectMapper,
                          ResponseRepository responseRepository,
                          RestHighLevelClient restHighLevelClient) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.objectMapper = objectMapper;
        this.responseRepository = responseRepository;
        this.restHighLevelClient = restHighLevelClient;
    }

    @GetMapping("/load")
    public String loadSampleData() {
        for (int x = 0; x < 10; x++) {
            String surveyId = String.valueOf(RandomUtils.nextInt(100, 103));
            String user = USERNAMES.get(RandomUtils.nextInt(0, USERNAMES.size()));

            List<Response> responses = IntStream.rangeClosed(0, 9).mapToObj(j -> {
                Response response = new Response();
                response.setRating(RandomUtils.nextInt(0, 11));
                response.setResponseId(UUID.randomUUID().toString());
                response.setSurveyId(surveyId);
                response.setQuestionNumber(j);
                response.setUser(user);
                List<String> words = IntStream.rangeClosed(0, RandomUtils.nextInt(5, 20))
                        .mapToObj(k -> WORDS.get(RandomUtils.nextInt(0, WORDS.size())))
                        .collect(Collectors.toList());
                response.setResponseText(String.join(" ", words));
                return response;
            }).collect(Collectors.toList());

            responseRepository.saveAll(responses);
        }
        return "responses loaded!";
    }

    //ElasticsearchRespository - High level.  Easy to use for simpler operations.
    @GetMapping("/survey/{surveyId}")
    public List<Response> getResponsesForSurveyId(@PathVariable String surveyId) {
        PageRequest page = PageRequest.of(0, 10);
        return responseRepository.findBySurveyId(surveyId, page).toList();
    }

    //ElasticsearchRestTemplate - Provides some shortcuts and adds simplicity.
    @GetMapping("/rating/{low}/{high}")
    public List<Response> getResponsesWithRatingsBetween(@PathVariable Integer low, @PathVariable Integer high) {
        Query searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.rangeQuery("rating")
                        .gte(low)
                        .lte(high)
        ).build();

        SearchHits<Response> hits = elasticsearchRestTemplate.search(searchQuery, Response.class, IndexCoordinates.of("responses"));

        return hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    // RestHighLevelClient (is lower level than the others).
    @GetMapping("/text/{keyword}")
    public List<Response> getResponsesWithKeyword(@PathVariable String keyword) throws IOException {
        MatchQueryBuilder match = new MatchQueryBuilder("responseText", keyword);
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(match);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Arrays
                .stream(response.getHits().getHits())
                .map(h -> convert(h.getSourceAsString()))
                .collect(Collectors.toList());
    }

    private Response convert(String json) {
        try {
            return objectMapper.readValue(json, Response.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            //should really do something here!
        }
    }
}
