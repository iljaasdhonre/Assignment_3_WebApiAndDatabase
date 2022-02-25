package com.richieandmod.assignment_3_webapianddatabase.Models;

import javax.persistence.*;

@Entity
@Table(name = "franchises")
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer franchiseId;

    @Column
    public String name;

    @Column
    public String description;
}
