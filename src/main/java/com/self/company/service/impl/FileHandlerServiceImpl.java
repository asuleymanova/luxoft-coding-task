package com.self.company.service.impl;

import com.self.company.model.User;
import com.self.company.repository.UserRepository;
import com.self.company.service.FileHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileHandlerServiceImpl implements FileHandlerService {

    public static List<String> HEADERS = List.of("PRIMARY_KEY","NAME","DESCRIPTION","UPDATED_TIMESTAMP");

    private final UserRepository userRepository;

    /**
     * Parse file and save to database as requested
     * at coding task at line REQ-01:
     */

    @Override
    public void uploadAndSaveFileToDatabase(MultipartFile file) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()
                             .withIgnoreHeaderCase().withTrim())) {

            if (!csvParser.getHeaderNames().equals(HEADERS)) {
                throw new IllegalStateException("HEADERS are not in the expected FORM: " + HEADERS);
            }

            List<User> userList = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser.getRecords()) {
                parseRecord(csvRecord, userList);
            }

            userRepository.saveAll(userList);
        } catch (Exception e) {
            log.error("Error while uploading and saving file to database: " + e);
        }
    }

    /**
     * Validate each record and skip lines that contain errors. Save only valid records as requested
     * at coding task at line REQ-04:
     *
     * @param csvRecord line at the text file
     * @param userList  list of users to be saved to database
     */
    public void parseRecord(CSVRecord csvRecord, List<User> userList) {
        try {
                User user = new User(csvRecord);
                userList.add(user);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage() + ". Record Number: " +  csvRecord.getRecordNumber(), e);
        } catch (Exception e) {
            log.error("SOMETHING IS WRONG AT LINE " + csvRecord.getRecordNumber(), e);
        }
    }

}

