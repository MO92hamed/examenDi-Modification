package com.examenspring.examendi.service;

import com.examenspring.examendi.model.Livre;
import com.examenspring.examendi.repository.LivreRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LivreServiceTest {

    @Autowired
    LivreService livreService;

    @MockBean
    LivreRepository livreRepository;

    @Test
    public void findByTitleNotFoundTest(){
        Optional<Livre> livreParam = Optional.of(new Livre(18L,"Welcome to task 4", "lfkjdfghdfgldfgjkdfgh"));
        Mockito.when(livreRepository.findByTitle(Mockito.anyString())).thenReturn(livreParam);

        Optional<Livre> livre = livreService.findLivreByTitle("Welcome to task 4");

        assertEquals(true, livre.isPresent());
        assertEquals("Welcome to task 4", livre.get().getTitle());
    }
}
