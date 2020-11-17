package com.optum.devdays.es.config;

import org.apache.http.HttpHost;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.SSLInitializationException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.stream.Stream;

@Configuration
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    private final String hostsString;

    @Value("${elasticsearch.index}")
    private String indexName;


    private RestHighLevelClient client;

    private final Integer port;
    private final String keystoreLocation;
    private final String keystorePassword;
    private final String truststoreLocation;
    private final String truststorePassword;

    @Bean
    public String indexName(){
        return indexName;
    }

    @Bean
    public RestHighLevelClient client() throws NoSuchAlgorithmException, KeyManagementException {
        return restHighLevelClient();
    }

    public AppConfig(@Value("${elastic.host}") String hostsString,
                     @Value("${elastic.port}") Integer port,
                     @Value("${app.elasticsearch.keystore.location:empty}") String keystoreLocation,
                     @Value("${app.elasticsearch.keystore.password:empty}") String keystorePassword,
                     @Value("${app.elasticsearch.truststore.location:empty}") String truststoreLocation,
                     @Value("${app.elasticsearch.truststore.password:empty}") String truststorePassword) {
        this.hostsString = hostsString;
        this.port = port;
        this.keystoreLocation = keystoreLocation;
        this.keystorePassword = keystorePassword;
        this.truststoreLocation = truststoreLocation;
        this.truststorePassword = truststorePassword;
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client) {
        return new ElasticsearchRestTemplate(client);
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() throws KeyManagementException, NoSuchAlgorithmException {
        boolean isSsl = !("empty".equals(keystoreLocation));
        boolean inAWS = hostsString.contains("amazonaws");

        RestClientBuilder restClientBuilder = null;
        HttpHost[] hosts = Stream.of(hostsString.split(","))
                .map(s -> new HttpHost(s.trim(), port, isSsl ? "https" : "http"))
                .toArray(HttpHost[]::new);
        LOG.info("restHighLevelClient setup");

        if (isSsl) { //keystore information has been specified
            SSLContext sslContext = null;

            if (inAWS) {
                sslContext = SSLContexts.custom().build();
            } else {
                sslContext = buildSSLContext(truststoreLocation, truststorePassword, keystoreLocation, keystorePassword);
            }

            LOG.info("inAWS: {}, sslContext:{}", inAWS, sslContext.toString());

            SSLContext finalSslContext = sslContext;
            restClientBuilder = RestClient.builder(hosts)
                    .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setSSLContext(finalSslContext));
        } else { //don't use ssl
            restClientBuilder = RestClient.builder(hosts);
        }

        return new RestHighLevelClient(restClientBuilder);
    }

    private SSLContext buildSSLContext(String truststoreFile, String truststorePassword, String keystoreFile, String keystorePassword) {
        try {
            SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                    .loadTrustMaterial(new File(truststoreFile), truststorePassword.toCharArray())
                    .loadKeyMaterial(new File(keystoreFile), keystorePassword.toCharArray(), keystorePassword.toCharArray());

            return sslContextBuilder.build();
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | CertificateException | IOException | KeyManagementException e) {
            throw new SSLInitializationException("Error setting up Elasticsearch SSL", e);
        }
    }

}
