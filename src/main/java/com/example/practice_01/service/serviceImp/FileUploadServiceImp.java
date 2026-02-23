package com.example.practice_01.service.serviceImp;

import com.example.practice_01.config.file.FileConfig;
import com.example.practice_01.exception.InvalidExceptionClass;
import com.example.practice_01.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImp implements FileUploadService {

    private final FileConfig fileConfig;

    @Override
    public String uploadFile(MultipartFile image)  {
        final Path root = Paths.get(fileConfig.getUploadPath());
        try {
            //get file
            String fileName = image.getOriginalFilename().toLowerCase();
            if (fileName != null &&
                    fileName.contains(".jpg") ||
                    fileName.contains(".png") ||
                    fileName.contains(".jpeg")||
                    fileName.contains(".heic")
            ){
                fileName = UUID.randomUUID()+"."+ StringUtils.getFilenameExtension(fileName);
                if (!Files.exists(root)){
                    Files.createDirectories(root);
                }
                Files.copy(image.getInputStream(),root.resolve(fileName) , StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            }else {
                throw  new InvalidExceptionClass("File should be image file");
            }
        }catch (IOException e){
            throw  new InvalidExceptionClass("File should be image file");
        }

    }
}
