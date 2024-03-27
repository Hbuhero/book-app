package com.book.management.Repository;

import com.book.management.Model.File;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByFilename(String filename);

    List<File> findByAuthor(String author);


    @Query(value = "SELECT * FROM files WHERE publisher_id = ?1", nativeQuery = true)
    List<File> findBooksByPublisherId(Long publisherID);

    @Query(value = "SELECT * FROM files WHERE filename LIKE %?1%", nativeQuery = true)
    List<File> findFilesByName(String filename);

    @Query(value = "SELECT * FROM files WHERE category = UPPER(?1)", nativeQuery = true)
    List<File> findByCategory(String category, Page page);
}
