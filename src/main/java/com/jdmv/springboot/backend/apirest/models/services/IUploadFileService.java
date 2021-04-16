package com.jdmv.springboot.backend.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	public Resource downloadImage(String imageName) throws MalformedURLException;
	public String saveImage(MultipartFile image) throws IOException;
	public boolean deleteImage(String imageName);
	public Path getImagePath(String imageName);

}
