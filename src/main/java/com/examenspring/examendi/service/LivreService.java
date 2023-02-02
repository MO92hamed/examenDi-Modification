package com.examenspring.examendi.service;

import com.examenspring.examendi.exception.ConflictException;
import com.examenspring.examendi.exception.RessourceNotFoundException;
import com.examenspring.examendi.model.Livre;
import com.examenspring.examendi.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class LivreService {
    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public Livre findLivreById(Long id){
          return livreRepository
                  .findById(id)
                  .orElseThrow(() -> new RessourceNotFoundException(String.format("No record with the id [%s] was found in our database..", id)));

    }

    public List<Livre> findAllLivres(){
        return livreRepository.findAll();
    }

    /*public List<Livre> findLivreByTitle(Optional<String> title){
        return livreRepository.findByTitle(title.orElseThrow(() -> new RessourceNotFoundException(String.format("title with title [%s] is not found", title))));
    }*/

    public Optional<Livre> findLivreByTitle(String title){

            return Optional.ofNullable(livreRepository.findByTitle(title).orElseThrow(() -> new RessourceNotFoundException(String.format("No Record with the title [%s] was found in our database ", title))));


    }


    public void deleteById(Long id){
        Optional<Livre> livre = livreRepository.findById(id);
        if (livre.isPresent())
            livreRepository.deleteById(id);
        else
            throw new RessourceNotFoundException(String.format("Cannot Delete livre with id [%s] .. not found id..", id));
    }

    public void deleteAll(){
        livreRepository.deleteAll();
    }

    public Livre createNewLivre(Livre livre){
        if(livreRepository.findByTitle(livre.getTitle()) != null){
            throw new ConflictException("Another record with the same title exists in our database");
        }
        return livreRepository.save(livre);
    }

    public Livre updateById(Long id, Livre livre){
        Livre book = livreRepository
                .findById(id).
                orElseThrow(() -> new RessourceNotFoundException(String.format("Livre  with id: [%s] is not found..", id)));

        book.setTitle(livre.getTitle());
        book.setDescription(livre.getDescription());

        return livreRepository.save(book);
    }
}
