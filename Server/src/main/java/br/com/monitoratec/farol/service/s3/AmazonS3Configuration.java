package br.com.monitoratec.farol.service.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Configuration {
    @Bean
    public AmazonS3 s3Client(@Value("${aws.s3-access-key}") String accessKey, @Value("${aws.s3-secret-key}") String secret) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secret)))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
