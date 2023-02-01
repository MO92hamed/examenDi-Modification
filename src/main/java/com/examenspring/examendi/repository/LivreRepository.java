package com.examenspring.examendi.repository;

import com.examenspring.examendi.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    //@Query(value = "select l from Livre l where title like %?1%")
    /*List<Livre> findByTitle(String title);*/
    Livre findByTitle(String title);

    /*Optional<Livre> findByTitle(String title);*/

}
