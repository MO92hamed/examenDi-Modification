package com.examenspring.examendi.service;

import com.examenspring.examendi.repository.LivreRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class LivreServiceTest {

    @Autowired
    LivreService livreService;

    @MockBean
    LivreRepository livreRepository;

    @Test
    public void whenFindAllLivres_ReturnLivreList(){

    }
}
