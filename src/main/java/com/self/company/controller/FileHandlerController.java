package com.self.company.controller;

import com.self.company.service.FileHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/files/")
@RequiredArgsConstructor
public class FileHandlerController {

    private final FileHandlerService fileHandlerService;

    @PostMapping("upload")
    public void uploadFile(@RequestParam MultipartFile file) {
        fileHandlerService.uploadAndSaveFileToDatabase(file);
    }
}
