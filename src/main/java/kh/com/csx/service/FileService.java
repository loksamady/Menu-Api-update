package kh.com.csx.service;

import kh.com.csx.dto.FileDTO;
import kh.com.csx.exception.APIException;
import kh.com.csx.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    @Value("${file.upload-merchant-logo-dir:uploads/merchant_logos}")
    private String uploadDir;

    private static FileDTO getAttachFile(String fileName, Resource resource) {
        LOGGER.info("media: {}, file name: {}", getMediaType(fileName), fileName);
        return createFileDTO(fileName, resource, getMediaType(fileName), false);
    }

    private static FileDTO createFileDTO(String fileName, Resource resource, MediaType mediaType, Boolean isDownload) {
        String headerValue = isDownload ? "attachment; filename=\"" + fileName + "\"" : null;
        return new FileDTO(fileName, resource, mediaType, headerValue);
    }

    private static MediaType getMediaType(String fileName) {
        return MediaType.parseMediaType(Util.getMimeType(fileName));
    }

    public FileDTO view(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName);

            LOGGER.info("file upload path: {}", path);

            return getAttachFile(fileName, new UrlResource(path.toUri()));
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new APIException(e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    public void save(MultipartFile file, String fileName) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (Files.notExists(uploadPath)) Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
