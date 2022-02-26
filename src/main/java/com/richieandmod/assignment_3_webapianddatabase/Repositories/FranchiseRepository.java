package com.richieandmod.assignment_3_webapianddatabase.Repositories;

import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {
}
