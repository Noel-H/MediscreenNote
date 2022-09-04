package com.noelh.mediscreennote.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Note model
 */
@Data
@Document
public class Note {

    @Id
    private String id;

    private Long patientId;
    private String noteOfThePractitioner;
}
