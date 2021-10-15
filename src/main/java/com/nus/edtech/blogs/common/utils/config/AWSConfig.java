package com.nus.edtech.blogs.common.utils.config;

import com.amazon.dax.client.dynamodbv2.AmazonDaxClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;
import java.util.Map;

@Configuration
public class AWSConfig {
    private String DAX_ENDPOINT = "mydaxcluster.mxyuv9.dax-clusters.ap-southeast-1.amazonaws.com:8111";

    @Value("${amazon.region}")
    private String awsRegion;

    @Value("${amazon.end-point.url}")
    private String awsDynamoDBEndPoint;

    public AWSCredentialsProvider amazonAWSCredentialsProvider() throws Exception {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    public DynamoDBMapper daxMapper(AmazonDynamoDB amazonDAX) {
        return new DynamoDBMapper(amazonDAX);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() throws Exception {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint, awsRegion))
                .withCredentials(amazonAWSCredentialsProvider())
                .build();
    }

    @Bean
    public AmazonDynamoDB amazonDAX() {
        AmazonDaxClientBuilder daxClientBuilder = AmazonDaxClientBuilder.standard();
        daxClientBuilder.withRegion(awsRegion).withEndpointConfiguration(DAX_ENDPOINT);

        return daxClientBuilder.build();
    }

    @Bean
    public static AWSCredentials amazonAWSCredentials() throws Exception {
        String msg = "Cannot load AWS credentials, no 'default' profile available.";
        try {
            return  new DefaultAWSCredentialsProviderChain().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(msg, e);
        }
    }

    @Bean
    public CloudWatchAsyncClient cloudWatchAsyncClient() {
        return CloudWatchAsyncClient.builder().region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create()).build();
    }

    @Bean
    public MeterRegistry getMeterRegistry() {
        CloudWatchConfig cloudWatchConfig = setupCloudWatchConfig();

        MeterRegistry meterRegistry = new CloudWatchMeterRegistry(cloudWatchConfig, Clock.SYSTEM,
                cloudWatchAsyncClient());

        return meterRegistry;
    }

    private CloudWatchConfig setupCloudWatchConfig() {
        CloudWatchConfig cloudWatchConfig = new CloudWatchConfig() {

            private Map<String, String> configuration
                    = Map.of("cloudwatch.namespace", "blogsApp",
                    "cloudwatch.step", Duration.ofMinutes(1).toString());

            @Override
            public String get(String key) {
                return configuration.get(key);
            }
        };
        return cloudWatchConfig;
    }
}


