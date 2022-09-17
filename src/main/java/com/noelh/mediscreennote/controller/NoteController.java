package com.noelh.mediscreennote.controller;

import com.noelh.mediscreennote.dto.NoteDTO;
import com.noelh.mediscreennote.model.Note;
import com.noelh.mediscreennote.service.NoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Note Controller
 */
@Slf4j
@RestController
@RequestMapping("/note")
@Tag(name = "NoteController", description = "Api pour les opérations CRUD et plus sur les notes.")
public class NoteController {

    private final NoteService noteService;

    /**
     * Note controller constructor
     * @param noteService used to perform operation
     */
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    /**
     * Get a list of note
     * @return a list who contains every note
     */
    @ApiOperation("Récupère une liste de toutes les notes")
    @GetMapping("")
    public List<Note> getNoteList(){
        log.info("GET /note");
        return noteService.getNoteList();
    }

    /**
     * Get a note by an id
     * @param id used to get the wanted note
     * @return a note or an error if the id is not found
     */
    @ApiOperation("Récupère une note grâce à un id donné")
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id){
        log.info("GET /note/{}", id);
        try {
            return ResponseEntity.ok(noteService.getNoteById(id));
        } catch (NoSuchElementException e) {
            log.error("GET /note/{} | [ERROR] : {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of note by a patientId
     * @param patientId used to get the wanted list of note
     * @return a list of note or an error if the patientId is not found
     */
    @ApiOperation("Récupère une liste de note grâce à un patientId donné")
    @GetMapping("/patientId/{patientId}")
    public ResponseEntity<List<Note>> getNoteListByPatientId(@PathVariable("patientId") Long patientId){
        log.info("GET /note/patientId/{}", patientId);
        try {
            return ResponseEntity.ok(noteService.getNoteListByPatientId(patientId));
        } catch (NoSuchElementException e){
            log.error("GET /note/patientId/{} | [ERROR] : {}", patientId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a new note
     * @param noteDTO used to save the added a note
     * @return the note
     */
    @ApiOperation("Ajoute une nouvelle note")
    @PostMapping("")
    public ResponseEntity<Note> postNote(@RequestBody NoteDTO noteDTO){
        log.info("POST /note");
        return ResponseEntity.ok(noteService.addNote(noteDTO));
    }

    /**
     * Update a note by an id
     * @param id used to update the wanted note
     * @param noteDTO used to save the updated note
     * @return the updated note or an error if the id is not found
     */
    @ApiOperation("Modifie une note déjà existante grâce à un id donné")
    @PutMapping("/{id}")
    public ResponseEntity<Note> putNoteById(@PathVariable("id") String id,@RequestBody NoteDTO noteDTO){
        log.info("PUT /note/{}", id);
        try {
            return ResponseEntity.ok(noteService.updateNote(id, noteDTO));
        } catch (NoSuchElementException e) {
            log.error("PUT /note/{} | [ERROR] : {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a note by an id
     * @param id used to delete the wanted note
     * @return the deleted note or an error if the id is not found
     */
    @ApiOperation("Supprime une note grâce à un id donné")
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNoteById(@PathVariable("id") String id){
        log.info("DELETE /note/{}", id);
        try {
            return ResponseEntity.ok(noteService.deleteNote(id));
        } catch (NoSuchElementException e) {
            log.error("DELETE /note/{} | [ERROR] : {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a list of note by a patientId
     * @param patientId used to delete the wanted list of note
     * @return the deleted list of note or an error if the patientId is not found
     */
    @ApiOperation("Supprime une liste de note grâce à un patientId donné")
    @DeleteMapping("/patientId/{patientId}")
    public ResponseEntity<List<Note>> deleteNoteByPatientId(@PathVariable("patientId") Long patientId){
        log.info("DELETE /note/patientId/{}", patientId);
        try {
            return ResponseEntity.ok(noteService.deleteNoteByPatientId(patientId));
        } catch (NoSuchElementException e){
            log.error("DELETE /note/patientId/{} | [ERROR] : {}", patientId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
