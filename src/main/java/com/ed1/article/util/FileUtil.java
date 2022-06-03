package com.ed1.article.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ed1.article.dto.FileResponseDTO;


@Component
public class FileUtil {
	
	@Value("${upload.directory}")
	private String uploadDirectory;
	
	private static String UPLOAD_PATH;
	
	@Value("${upload.directory}")
    public void setUploadPathStatic(String uploadPath) {
		UPLOAD_PATH = uploadPath;
    }
	
	public static String getFileExtension(String fileName) {
		Integer i = fileName.lastIndexOf(".");
		return fileName.substring(i + 1);
	}
	
	public void saveFile(String path, String fileName, MultipartFile file) {
		Path uploadPath = Paths.get(uploadDirectory + path);
		
		try (InputStream inputStream = file.getInputStream()){
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Problems trying to save file: " + fileName);
		}	
	}
	
	public static boolean removeFile(String path) {
		File file = new File(UPLOAD_PATH + path);
		return file.delete();
	}
	
	public static FileResponseDTO generateUriFrom(String file, Long id) {
		if (file == null || file.length() == 0 || id == null)
			return null;
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/resources/")
				.path(file)
				.toUriString();
		
		String type = getFileExtension(file);
		Long size = null;
		
		try {
			size = Files.size(Paths.get(UPLOAD_PATH + file));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new FileResponseDTO(file, fileDownloadUri, type, size);
	}

// User methods
//	public void saveUserPhoto(User entity, MultipartFile photo) {
//		String userPath = "user/" +  entity.getId() + "/";
//		String fileName = "photo." + getFileExtension(photo.getOriginalFilename());
//		
//		if(entity.getPhoto() != null) {
//			removeFile(entity.getPhoto());
//		}
//		
//		saveFile(userPath, fileName, photo);
//		entity.setPhoto(userPath + fileName);
//	}
//	
//	public void deleteUserPhoto(User entity) {
//		if(removeFile(entity.getPhoto())) 
//			entity.setPhoto(null);
//	}
	
}
