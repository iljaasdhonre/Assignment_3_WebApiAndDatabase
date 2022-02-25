package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/franchise")
public class FranchiseController {

    @Autowired
    FranchiseRepository franchiseRepository;

    //create
    @PostMapping("/add")
    public Franchise createFranchise(@RequestBody Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        return franchise;
    }

    //get
    @GetMapping("/{id}")
    public Franchise getFranchise(@PathVariable Integer franchiseId) {
        Franchise franchise = null;
        if(franchiseRepository.existsById(franchiseId)) {
            franchise = franchiseRepository.findById(franchiseId).get();
        }
        return franchise;
    }
}
