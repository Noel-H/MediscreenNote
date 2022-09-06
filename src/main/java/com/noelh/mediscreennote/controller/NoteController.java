package com.noelh.mediscreennote.controller;

import com.noelh.mediscreennote.dto.NoteDTO;
import com.noelh.mediscreennote.model.Note;
import com.noelh.mediscreennote.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping("")
    public List<Note> getNoteList(){
        log.info("GET /note");
        return noteService.getNoteList();
    }

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

    @PostMapping("")
    public ResponseEntity<Note> postNote(@RequestBody NoteDTO noteDTO){
        log.info("POST /note");
        return ResponseEntity.ok(noteService.addNote(noteDTO));
    }

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

    @DeleteMapping("patientId/{patientId}")
    public List<Note> deleteNoteByPatientId(@PathVariable("patientId") Long patientId){
        log.info("DELETE /note/patientId/{}", patientId);
            return noteService.deleteNoteByPatientId(patientId);
    }
}
