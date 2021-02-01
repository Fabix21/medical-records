package com.medicalrecords.medicalrecords.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class AmazonClientService {

    private static final Logger logger = LoggerFactory.getLogger(AmazonClientService.class);

    private AmazonS3 s3client;

    @Value("${s3.endpointUrl}")
    private String endpointUrl;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.accessKeyId}")
    private String accessKeyId;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId,secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile( MultipartFile multipartFile ) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        uploadFileTos3bucket(fileName,file);
        file.delete();
        return fileName;
    }

    private void uploadFileTos3bucket( String fileName,File file ) {
        s3client.putObject(bucketName,fileName,file);
    }

    private File convertMultiPartToFile( MultipartFile file ) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String generateFileName( MultipartFile multiPart ) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ","_");
    }

    public byte[] downloadFile( final String keyName ) {
        byte[] content = null;
        logger.info("Downloading an object with key= {}",keyName);
        final S3Object s3Object = s3client.getObject(bucketName,keyName);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            logger.info("File downloaded successfully.");
            s3Object.close();
        } catch (final IOException ex) {
            logger.info("IO Error Message= {}",ex.getMessage());
        }
        return content;
    }

    public String deleteFileFromS3Bucket( String fileUrl ) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        s3client.deleteObject(bucketName,fileName);
        return "Successfully deleted";
    }

}
