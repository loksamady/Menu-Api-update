package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.entity.MenuFile;
import kh.com.csx.repository.MenuFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuFileService {
    private final MenuFileRepository menuFileRepository;
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public MenuFileService(MenuFileRepository menuFileRepository) {
        this.menuFileRepository = menuFileRepository;
    }

    @Transactional
    public List<MenuFile> saveFiles(List<MultipartFile> files) throws IOException {
        List<MenuFile> savedFiles = new ArrayList<>();

        // Ensure upload directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (Files.notExists(uploadPath)) Files.createDirectories(uploadPath);

        files.forEach(file -> {
            // Generate unique filename (optional: to avoid collision)
            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            // Save file physically
            Path filePath = uploadPath.resolve(fileName);

            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Build MenuFile entity
            MenuFile menuFile = MenuFile.builder().fileName(fileName).build();

            // Save entity
            savedFiles.add(menuFileRepository.save(menuFile));
        });

        return savedFiles;
    }

    public void deleteMenuFile(Long id) {
        menuFileRepository.deleteById(id);
    }
}
