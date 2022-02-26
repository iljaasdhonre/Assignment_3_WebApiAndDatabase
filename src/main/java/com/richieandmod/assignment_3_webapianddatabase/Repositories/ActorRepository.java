package com.richieandmod.assignment_3_webapianddatabase.Repositories;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
