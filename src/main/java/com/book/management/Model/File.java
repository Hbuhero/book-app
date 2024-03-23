package com.book.management.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

@Entity
@Table(name = "files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileID;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "fileUri", nullable = false)
    private String fileUri;

    @Column
    private String size;

    @Column(name = "upload-date")
    private String uploadDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Access access;

    @ManyToOne(targetEntity = Publisher.class)
    @JoinColumn(name = "publisher_id",referencedColumnName = "id")
    @JsonIgnoreProperties("files")
    private Publisher publisher;

    private String author;

//    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
//    @CollectionTable(
//            name = "file_category",
//            joinColumns = @JoinColumn(name = "file_id"))
//    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
//    private List<Category> book_category;
}
