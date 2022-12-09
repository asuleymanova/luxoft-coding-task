package com.self.company.controller;

import com.self.company.service.impl.FileHandlerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileHandlerController.class)
public class FileHandlerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileHandlerServiceImpl fileHandlerService;

    @Test
    void shouldReturnSuccessWhenUploadingFile() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("file.txt");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "file.txt",
                "application/octet-stream",
                classPathResource.getInputStream());
        mvc.perform(multipart("/files/upload").file(multipartFile))
                .andExpect(status().isOk());
    }

}
