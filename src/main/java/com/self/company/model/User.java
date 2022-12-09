package com.self.company.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.Validate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String name;

    private String description;

    private LocalDateTime updateTimeStamp;

    public User(CSVRecord csvRecord) {
        this.id =  Validate.notBlank(csvRecord.get("PRIMARY_KEY"), "PRIMARY_KEY could not be null");
        this.name = Optional.ofNullable(csvRecord.get("NAME")).orElse("");
        this.description = Optional.ofNullable(csvRecord.get("DESCRIPTION")).orElse("");
        this.updateTimeStamp =parseDate(csvRecord.get("UPDATED_TIMESTAMP"));
    }

    private LocalDateTime parseDate(String dateTime){
        if(!dateTime.isBlank()) {
            try {
                return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
            }catch (DateTimeParseException e){
                throw new IllegalArgumentException("Format of the date (should be \"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\") is wrong");
            }
        }
        return null;
    }
}
