package com.richieandmod.assignment_3_webapianddatabase.Repositories;

import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {

    //check if franchise exists by name
    boolean existsFranchiseByName(String name);

    //get the first occurrence of franchise by name
    Franchise findFirstByName(String name);

}
