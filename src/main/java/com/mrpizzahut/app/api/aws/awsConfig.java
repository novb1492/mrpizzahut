package com.mrpizzahut.app.api.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class awsConfig {
	  private String accessKey ="AKIAXVQHL24PHXPXKYU4";
	    private String secretKey ="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";

	   // private AmazonS3 s3Client;
	    
	    @Bean
	    public AmazonS3 S3Client() {
	        System.out.println(secretKey);
	        AWSCredentials credentials=new BasicAWSCredentials(accessKey, secretKey);
	        return AmazonS3ClientBuilder.standard()
	                                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                                    .withRegion("ap-northeast-2").build();
	    }
}
