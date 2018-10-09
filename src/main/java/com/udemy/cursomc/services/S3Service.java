package com.udemy.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.udemy.cursomc.resources.exception.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			String contentType = multipartFile.getContentType();
			InputStream in = multipartFile.getInputStream();
			return this.uploadFile(in, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}
	
	public URI uploadFile(InputStream in, String fileName, String contentType) {
		try {
			LOG.info("Iniciando upload...");
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			this.s3Client.putObject(this.bucketName, fileName, in, meta);
			LOG.info("Upload concluído!");

			return s3Client.getUrl(this.bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}
}
