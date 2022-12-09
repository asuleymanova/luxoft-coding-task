package com.self.company.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandlerService {
    void uploadAndSaveFileToDatabase(MultipartFile file);

}
