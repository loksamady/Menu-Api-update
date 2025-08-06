package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.entity.MerchantFile;
import kh.com.csx.repository.MerchantFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantFileService {
    private final MerchantFileRepository repository;
    private  final FileService fileService;

    public MerchantFileService(MerchantFileRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @Transactional
    public List<MerchantFile> saveFiles(List<MultipartFile> files) throws IOException {
        List<MerchantFile> savedFiles = new ArrayList<>();

        files.forEach(file -> {
            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            fileService.save(file, fileName);

            MerchantFile merchantFile = MerchantFile.builder().fileName(fileName).build();
            savedFiles.add(repository.save(merchantFile));
        });

        return savedFiles;
    }

    public void deleteFile(Integer id) {
        repository.deleteById(id);
    }
}
