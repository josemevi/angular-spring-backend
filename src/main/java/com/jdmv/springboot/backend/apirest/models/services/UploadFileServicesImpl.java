package com.jdmv.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServicesImpl implements IUploadFileService {
	
	private final Logger log = LoggerFactory.getLogger(UploadFileServicesImpl.class);
	
	private final static String DIRECTORIO_UPLOAD ="uploads";

	@Override
	public Resource downloadImage(String imageName) throws MalformedURLException {
		Path filePath = getImagePath(imageName);
		log.info(filePath.toString());
		Resource rsrc = new UrlResource(filePath.toUri());	
		
		if(!rsrc.exists() && !rsrc.isReadable()) {
			filePath = Paths.get("src/main/resources/static/images").resolve("no_photo.png").toAbsolutePath();
			rsrc = new UrlResource(filePath.toUri());	
			
			log.error("Error al cargar la imagen: "+imageName);
		}
		return rsrc;
	}

	@Override
	public String saveImage(MultipartFile image) throws IOException {
		//UUID is to specify a random identifier to the image, avoiding having the same names for images, we also delete any whitespaces
		String imageName = UUID.randomUUID().toString() +"_"+image.getOriginalFilename().replace(" ", "");
		//we specify the path in a variable Path type, the path string have to be in absolute uri e.g. C\\temp\\img. in this case we're saving in the root of the proyect.
		Path filePath = getImagePath(imageName);
		log.info(filePath.toString());
	
		//we copy the input stream into the path and assign the name
		Files.copy(image.getInputStream(), filePath);

		return imageName;
	}

	@Override
	public boolean deleteImage(String imageName) {
		
		if(imageName != null && imageName.length() > 0) {
			Path oldFilePath = Paths.get("uploads").resolve(imageName).toAbsolutePath();
			File oldPhoto = oldFilePath.toFile();
			if(oldPhoto.exists() && oldPhoto.canRead()) {
				oldPhoto.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getImagePath(String imageName) {
		// TODO Auto-generated method stub
		return Paths.get(DIRECTORIO_UPLOAD).resolve(imageName).toAbsolutePath();
	}

}
