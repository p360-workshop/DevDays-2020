package com.optum.devdays.es.repository;

import com.optum.devdays.es.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends ElasticsearchRepository<Response, String> {

    Page<Response> findBySurveyId(String surveyId, Pageable pageable);

}
