package com.richieandmod.assignment_3_webapianddatabase.Repositories;

import com.richieandmod.assignment_3_webapianddatabase.Models.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Integer> {
}
