package com.noelh.mediscreennote.service;

import com.noelh.mediscreennote.dto.NoteDTO;
import com.noelh.mediscreennote.model.Note;
import com.noelh.mediscreennote.proxies.MediscreenPatientProxy;
import com.noelh.mediscreennote.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Note Service
 */
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final MediscreenPatientProxy mediscreenPatientProxy;

    public NoteService(NoteRepository noteRepository, MediscreenPatientProxy mediscreenPatientProxy){
        this.noteRepository = noteRepository;
        this.mediscreenPatientProxy = mediscreenPatientProxy;
    }

    /**
     * Find all note
     * @return a list of note
     */
    public List<Note> getNoteList(){
        return noteRepository.findAll();
    }

    /**
     * Get a list of note by a patientId
     * @param patientId is the patientId of the note
     * @return a list of note
     */
    public List<Note> getNoteListByPatientId(Long patientId) {
        if (!mediscreenPatientProxy.isPatientIdExist(patientId)){
            throw new NoSuchElementException("PatientId not found : "+patientId);
        }
        return noteRepository.getNotesByPatientId(patientId);
    }

    /**
     * get a note by his id
     * @param id is the id of the note
     * @return the wanted note
     */
    public Note getNoteById(String id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Id not Found : " + id));
    }

    /**
     * Add a note
     * @param noteDTO is the dto who contains the required information to add
     * @return the added note
     */
    public Note addNote(NoteDTO noteDTO){
        Note note = new Note();
        note.setPatientId(noteDTO.getPatientId());
        note.setNoteOfThePractitioner(noteDTO.getNoteOfThePractitioner());
        return noteRepository.save(note);
    }

    /**
     * Update a note
     * @param id is the id of the note
     * @param noteDTO is the dto who contains the required information to update
     * @return the updated note
     */
    public Note updateNote(String id, NoteDTO noteDTO){
        Note note = getNoteById(id);
        note.setPatientId(noteDTO.getPatientId() == null ? note.getPatientId() : noteDTO.getPatientId());
        note.setNoteOfThePractitioner(noteDTO.getNoteOfThePractitioner() == null ? note.getNoteOfThePractitioner() : noteDTO.getNoteOfThePractitioner());
        return noteRepository.save(note);
    }

    /**
     * Delete a note
     * @param id is the id of the note
     * @return the deleted note
     */
    public Note deleteNote(String id){
        Note noteToDelete = getNoteById(id);
        Note note = new Note();
        note.setId(noteToDelete.getId());
        note.setPatientId(noteToDelete.getPatientId());
        note.setNoteOfThePractitioner(noteToDelete.getNoteOfThePractitioner());
        noteRepository.deleteById(id);
        return note;
    }

    public List<Note> deleteNoteByPatientId(Long patientId) {
        List<Note> noteList = getNoteListByPatientId(patientId);
        noteList.forEach(note -> deleteNote(note.getId()));
        return noteList;
    }
}
