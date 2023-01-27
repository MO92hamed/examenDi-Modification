package com.examenspring.examendi.service;

import com.examenspring.examendi.exception.RessourceNotFoundException;
import com.examenspring.examendi.model.Livre;
import com.examenspring.examendi.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
                  .orElseThrow(() -> new RessourceNotFoundException("Livre with id : " + id + " id not found .."));

    }

    public List<Livre> findAllLivres(){
        return livreRepository.findAll();
    }

    public List<Livre> findLivreByTitle(Optional<String> title){
        return livreRepository.findByTitle(title.orElse("_"));

    }

    public void deleteById(Long id){
        Optional<Livre> livre = livreRepository.findById(id);
        if (livre.isPresent())
            livreRepository.deleteById(id);
        else
            throw new RessourceNotFoundException("Cannot Delete livre with id: " + id + ": livre is not found");
    }

    public void deleteAll(){
        livreRepository.deleteAll();
    }

    public Livre createNewLivre(Livre livre){
        return livreRepository.save(livre);
    }

    public Livre updateById(Long id, Livre livre){
        Livre book = livreRepository
                .findById(id).
                orElseThrow(() -> new RessourceNotFoundException("Livre  with id: " + id + " is not found.."));

        book.setTitle(livre.getTitle());
        book.setDescription(livre.getDescription());

        return livreRepository.save(book);
    }
}
