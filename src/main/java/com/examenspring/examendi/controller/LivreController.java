package com.examenspring.examendi.controller;

import com.examenspring.examendi.model.Livre;
import com.examenspring.examendi.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/livres")
@CrossOrigin(origins = "http://localhost:8081")
public class LivreController {

    @Autowired
    LivreService livreService;

    // get Livre by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivre(@PathVariable Long id){
        Livre livre = livreService.findLivreById(id);
        return ResponseEntity.ok(livre);
    }

    // get all Livres
    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivres(){
        List<Livre> livres = livreService.findAllLivres();
        return ResponseEntity.ok(livres);
    }

    // get Livre by title rest api
   /*@GetMapping("/")
    public ResponseEntity<List<Livre>> getLivreByTitle(@RequestParam Optional<String> title){
        List<Livre> livres= livreService.findLivreByTitle(title);
        return ResponseEntity.ok(livres);
    }*/

    @GetMapping("/")
    public ResponseEntity<Optional<Livre>> getLivreByTitle(@RequestParam String title){
        Optional<Livre> livre= livreService.findByTitle(title);
        return ResponseEntity.ok(livre);
    }

    // delete Livre by id rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteLivreById(@PathVariable Long id){
        livreService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // delete all Livres rest api
    @DeleteMapping
    public ResponseEntity<Map<String, Boolean>> deleteAllLivres(){
        livreService.deleteAll();
        Map<String, Boolean> response = new HashMap<>();
        response.put("All Book are deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // create Livre rest api
    @PostMapping
    public  Livre addNewLivre(@Valid @RequestBody Livre livre){
        return livreService.createNewLivre(livre);
    }

    // update Livre by id rest api
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivreById(@Valid @PathVariable Long id, @RequestBody Livre livre){
        Livre updatedLivre = livreService.updateById(id, livre);
        return ResponseEntity.ok(updatedLivre);
    }
}
