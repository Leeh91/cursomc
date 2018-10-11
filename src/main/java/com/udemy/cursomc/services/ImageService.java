package com.udemy.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.cursomc.resources.exception.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		
		try {
			String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
			
			if(!extension.equals("jpg") && !extension.equals("png")) {
				throw new FileException("Somente imagens PNG e JPG são permitidas");
			}
			
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			
			if(extension.equals("png")) {
				img = this.pngToJpg(img);
			}
			
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo");
		}
	}
	
	public BufferedImage pngToJpg(BufferedImage image) {
		BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage image, String extension) {
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, extension, bos);
			return new ByteArrayInputStream(bos.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
		
	}
	
	public BufferedImage cropImage(BufferedImage srcImage) {
		int min = (srcImage.getHeight() <= srcImage.getWidth()) ? srcImage.getHeight() : srcImage.getWidth();
		
		return Scalr.crop(
				srcImage, 
				(srcImage.getWidth()/2) - min/2, 
				(srcImage.getHeight()/2) - min/2, min,min);
	}
	
	public BufferedImage resize(BufferedImage srcImage, int size) {
		return Scalr.resize(srcImage, Scalr.Method.ULTRA_QUALITY, size);
	}
	
}
