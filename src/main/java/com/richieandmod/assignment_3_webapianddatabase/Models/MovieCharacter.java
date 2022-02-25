package com.richieandmod.assignment_3_webapianddatabase.Models;

import javax.persistence.*;

@Entity
@Table(name = "characters")
public class MovieCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer characterId;

    @Column
    public String name;

    @Column
    public String alias;

    @Column
    public String gender;

    @Column
    public String pictureUrl;
}
