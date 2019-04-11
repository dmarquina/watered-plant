package com.dmarquina.wateredplant.service.impl;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dmarquina.wateredplant.service.AmazonService;
import com.dmarquina.wateredplant.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonServiceImpl implements AmazonService {

  @Resource
  Environment environment;

  private AmazonS3 amazonS3;

  @PostConstruct
  private void initializeAmazon() {
    this.amazonS3 = new AmazonS3Client(
        new BasicAWSCredentials(environment.getProperty("aws_access_key"),
                                environment.getProperty("aws_secret_key")));
  }

  @Override
  public String uploadFile(Long plantId, MultipartFile multipartFile) {
    String fileUrl = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
    String dateName = dateFormat.format(new Date());
    try {
      File file = convertMultipartToFile(multipartFile);
      String fileName = plantId.toString() + "-" + dateName;
      fileUrl = Constants.END_POINT_URL + "/" + Constants.AWS_BUCKET_NAME + "/" + fileName;

      amazonS3.putObject(
          new PutObjectRequest(Constants.AWS_BUCKET_NAME, fileName, file).withCannedAcl(
              CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return fileUrl;
  }

  private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
    File convertFile = new File(multipartFile.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convertFile);
    fos.write(multipartFile.getBytes());
    fos.close();
    return convertFile;
  }
}
