package com.book.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "publisher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    @Column
    private String lastname;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Long phoneNumber;
    @Column(nullable = true)
    private String street;
    @Column(nullable = true)
    private String country;
    @Column(nullable = true)
    private String postalCode;

    @OneToMany(mappedBy = "publisher")
    private List<File> files;
}
