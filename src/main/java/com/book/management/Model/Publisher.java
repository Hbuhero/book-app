package com.book.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "publisher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    @Column(unique = true)
    private String email;

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
    private String about;

    @OneToMany(mappedBy = "publisher")
    private List<File> files;
}
