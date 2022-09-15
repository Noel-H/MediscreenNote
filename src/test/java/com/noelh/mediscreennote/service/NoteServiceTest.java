package com.noelh.mediscreennote.service;

import com.noelh.mediscreennote.dto.NoteDTO;
import com.noelh.mediscreennote.model.Note;
import com.noelh.mediscreennote.proxies.MediscreenPatientProxy;
import com.noelh.mediscreennote.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private MediscreenPatientProxy mediscreenPatientProxy;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void getNoteList_Should_Call_NoteRepository_FindAll_Once(){
        //Given
        when(noteRepository.findAll()).thenReturn(new ArrayList<>());

        //When
        noteService.getNoteList();

        //Then
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    public void getNoteListByPatientId_Should_Call_NoteRepository_Once(){
        //Given
        when(mediscreenPatientProxy.isPatientIdExist(1L)).thenReturn(true);
        when(noteRepository.getNotesByPatientId(1L)).thenReturn(new ArrayList<>());

        //When
        noteService.getNoteListByPatientId(1L);

        //Then
        verify(noteRepository, times(1)).getNotesByPatientId(1L);
    }

    @Test
    public void getNoteListByPatientId_Should_Throw_NoSuchElementException(){
        //Given
        when(mediscreenPatientProxy.isPatientIdExist(1L)).thenReturn(false);

        //When

        //Then
        assertThrows(NoSuchElementException.class, () -> noteService.getNoteListByPatientId(1L));
    }

    @Test
    public void getNoteById_Should_Call_NoteRepository_FindById_Once(){
        //Given
        when(noteRepository.findById("id")).thenReturn(Optional.of(new Note()));

        //When
        noteService.getNoteById("id");

        //Then
        verify(noteRepository, times(1)).findById("id");
    }

    @Test
    public void getNoteById_Should_Throw_NoSuchElementException(){
        //Given
        when(noteRepository.findById("id")).thenReturn(Optional.empty());

        //When

        //Then
        assertThrows(NoSuchElementException.class, () -> noteService.getNoteById("id"));
    }

    @Test
    public void addNote_Should_Call_NoteRepository_Save_Once(){
        //Given
        NoteDTO noteDTO = new NoteDTO();
        Note note = new Note();
        when(noteRepository.save(note)).thenReturn(new Note());

        //When
        noteService.addNote(noteDTO);

        //Then
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void updateNote_Should_Call_NoteRepository_Save_Once(){
        //Given
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteOfThePractitioner("New test note");


        Note noteRequested = new Note();
        noteRequested.setId("id");
        noteRequested.setPatientId(1L);
        noteRequested.setNoteOfThePractitioner("Test note");

        Note note = new Note();
        note.setId("id");
        note.setPatientId(1L);
        note.setNoteOfThePractitioner("New test note");

        when(noteRepository.findById("id")).thenReturn(Optional.of(noteRequested));
        when(noteRepository.save(note)).thenReturn(new Note());

        //When
        noteService.updateNote("id",noteDTO);

        //Then
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void deleteNote_Should_Call_NoteRepository_DeleteById_Once(){
        //Given
        when(noteRepository.findById("id")).thenReturn(Optional.of(new Note()));
        doNothing().when(noteRepository).deleteById("id");

        //When
        noteService.deleteNote("id");

        //Then
        verify(noteRepository, times(1)).deleteById("id");
    }

    @Test
    public void deleteNoteByPatientId_Should_Call_NoteRepository_DeleteById_Once_Each_For_A_List_Of_Two_Note(){
        //Given
        Note note = new Note();
        note.setId("id");
        note.setPatientId(1L);
        note.setNoteOfThePractitioner("Test note");

        Note note2 = new Note();
        note2.setId("id2");
        note2.setPatientId(1L);
        note2.setNoteOfThePractitioner("Test note 2");

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        noteList.add(note2);

        when(mediscreenPatientProxy.isPatientIdExist(1L)).thenReturn(true);
        when(noteRepository.getNotesByPatientId(1L)).thenReturn(noteList);
        when(noteRepository.findById("id")).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).deleteById("id");
        when(noteRepository.findById("id2")).thenReturn(Optional.of(note2));
        doNothing().when(noteRepository).deleteById("id2");

        //When
        noteService.deleteNoteByPatientId(1L);

        //Then
        verify(noteRepository, times(1)).deleteById("id");
        verify(noteRepository, times(1)).deleteById("id2");
    }

}