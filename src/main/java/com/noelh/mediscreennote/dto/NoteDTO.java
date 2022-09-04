package com.noelh.mediscreennote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Note DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    private Long PatientId;
    private String noteOfThePractitioner;
}
