package com.noelh.mediscreennote.controller;

import com.noelh.mediscreennote.dto.NoteDTO;
import com.noelh.mediscreennote.model.Note;
import com.noelh.mediscreennote.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @Test
    public void getNoteList_Should_Call_NoteService_Once(){
        //Given
        when(noteService.getNoteList()).thenReturn(new ArrayList<>());

        //When
        noteController.getNoteList();

        //Then
        verify(noteService, times(1)).getNoteList();
    }

    @Test
    public void getNoteById_Should_Return_Ok(){
        //Given
        Note note = new Note();
        when(noteService.getNoteById("Test")).thenReturn(note);

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(note, HttpStatus.OK);

        //When
        ResponseEntity<Note> result = noteController.getNoteById("Test");

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void getNoteById_Should_Return_NotFound(){
        //Given
        when(noteService.getNoteById("Test")).thenThrow(new NoSuchElementException());

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //When
        ResponseEntity<Note> result = noteController.getNoteById("Test");

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void getNoteListByPatientId_Should_Return_Ok(){
        //Given
        List<Note> noteList = new ArrayList<>();
        when(noteService.getNoteListByPatientId(1L)).thenReturn(noteList);

        ResponseEntity<List<Note>> expectedResult = new ResponseEntity<>(noteList, HttpStatus.OK);

        //When
        ResponseEntity<List<Note>> result = noteController.getNoteListByPatientId(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void getNoteListByPatientId_Should_Return_NotFound(){
        //Given
        when(noteService.getNoteListByPatientId(1L)).thenThrow(new NoSuchElementException());

        ResponseEntity<List<Note>> expectedResult = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //When
        ResponseEntity<List<Note>> result = noteController.getNoteListByPatientId(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void postNote_Should_return_Ok(){
        //Given
        NoteDTO noteDTO = new NoteDTO();
        Note note = new Note();
        when(noteService.addNote(noteDTO)).thenReturn(note);

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(note, HttpStatus.OK);

        //When
        ResponseEntity<Note> result = noteController.postNote(noteDTO);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void putNoteById_Should_Return_Ok(){
        //Given
        NoteDTO noteDTO = new NoteDTO();
        Note note = new Note();
        when(noteService.updateNote("Test", noteDTO)).thenReturn(note);

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(note, HttpStatus.OK);

        //When
        ResponseEntity<Note> result = noteController.putNoteById("Test", noteDTO);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void putNoteById_Should_Return_NotFound(){
        //Given
        NoteDTO noteDTO = new NoteDTO();
        when(noteService.updateNote("Test", noteDTO)).thenThrow(new NoSuchElementException());

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //When
        ResponseEntity<Note> result = noteController.putNoteById("Test", noteDTO);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void deleteNoteById_Should_Return_Ok(){
        //Given
        Note note = new Note();
        when(noteService.deleteNote("Test")).thenReturn(note);

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(note, HttpStatus.OK);

        //When
        ResponseEntity<Note> result = noteController.deleteNoteById("Test");

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void deleteNoteById_Should_Return_NotFound(){
        //Given
        when(noteService.deleteNote("Test")).thenThrow(new NoSuchElementException());

        ResponseEntity<Note> expectedResult = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //When
        ResponseEntity<Note> result = noteController.deleteNoteById("Test");

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void deleteNoteByPatientId_Should_Return_Ok(){
        //Given
        List<Note> noteList = new ArrayList<>();
        when(noteService.deleteNoteByPatientId(1L)).thenReturn(noteList);

        ResponseEntity<List<Note>> expectedResult = new ResponseEntity<>(noteList, HttpStatus.OK);

        //When
        ResponseEntity<List<Note>> result = noteController.deleteNoteByPatientId(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void deleteNoteByPatientId_Should_Return_NotFound(){
        //Given
        when(noteService.deleteNoteByPatientId(1L)).thenThrow(new NoSuchElementException());

        ResponseEntity<List<Note>> expectedResult = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //When
        ResponseEntity<List<Note>> result = noteController.deleteNoteByPatientId(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

}