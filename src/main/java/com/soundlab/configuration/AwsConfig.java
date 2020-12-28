package com.soundlab.configuration;

import com.soundlab.utils.DurationMetric;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    private String region;

    public AwsConfig(@Value("${aws.service.region}") String region) {
        this.region = region;
    }

    @Bean
    public SqsClient getSqsClient() {
        DurationMetric duration = new DurationMetric();
        SqsClient client = SqsClient.builder()
            .region(Region.of(region))
            .httpClient(UrlConnectionHttpClient.builder().build())
            .build();
        duration.measure("AWS SQS client initialization completed in");
        return client;
    }
}
