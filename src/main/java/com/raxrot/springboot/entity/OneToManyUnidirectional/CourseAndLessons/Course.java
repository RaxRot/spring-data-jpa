package com.raxrot.springboot.entity.OneToManyUnidirectional.CourseAndLessons;

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
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Set<Lesson> lessons = new HashSet<>();
}
