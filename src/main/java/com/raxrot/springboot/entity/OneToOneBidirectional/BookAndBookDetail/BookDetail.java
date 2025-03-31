package com.raxrot.springboot.entity.OneToOneBidirectional.BookAndBookDetail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_details")
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int pages;
    private String description;

    @OneToOne(mappedBy = "bookDetail")
    private Book book;
}
