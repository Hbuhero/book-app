package com.book.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.nio.file.Path;

@Entity
@Table(name = "files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fileID;

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
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
