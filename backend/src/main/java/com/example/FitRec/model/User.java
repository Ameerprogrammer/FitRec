package com.example.FitRec.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //id is primary key identifier; unique for everyone
    private Long id;
    private String password;
    private String gender;
    private int age;
    @Column(name = "style_preferences")
    private String stylePreferences;
    private String email;
    private String name;
    private String bodyType;
    private String occasion;
}
