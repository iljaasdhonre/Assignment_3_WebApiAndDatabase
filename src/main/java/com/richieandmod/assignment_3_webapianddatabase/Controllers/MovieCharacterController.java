package com.richieandmod.assignment_3_webapianddatabase.Controllers;


import com.richieandmod.assignment_3_webapianddatabase.Models.MovieCharacter;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/character")
public class MovieCharacterController {

    @Autowired
    private MovieCharacterRepository movieCharacterRepository;

    //create
    @PostMapping("/add")
    public MovieCharacter createCharacter(@RequestBody MovieCharacter movieCharacter) {
        movieCharacter = movieCharacterRepository.save(movieCharacter);
        return movieCharacter;
    }

    //get
    @GetMapping("/{id}")
    public MovieCharacter getCharacter(@PathVariable Integer characterId) {
        MovieCharacter movieCharacter = null;
        if(movieCharacterRepository.existsById(characterId)) {
            movieCharacter = movieCharacterRepository.findById(characterId).get();
        }
        return movieCharacter;
    }
}
