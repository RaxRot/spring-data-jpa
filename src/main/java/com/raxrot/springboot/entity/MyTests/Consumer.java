package com.raxrot.springboot.entity.MyTests;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consumers")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String email;

    @OneToMany(mappedBy = "consumer")
    private Set<Article> articles=new HashSet<>();

    @OneToMany(mappedBy = "consumer")
    private Set<Comment> comments=new HashSet<>();
}
