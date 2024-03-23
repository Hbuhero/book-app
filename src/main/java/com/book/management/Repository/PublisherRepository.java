package com.book.management.Repository;

import com.book.management.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);

    String findNameByName(String name);

    String findUsernameByName(String username);

    Optional<Publisher> findByUsername(String username);

    @Query(value = "SELECT id FROM publisher WHERE name = ?1", nativeQuery = true)
    Long findIdByName(String publisher);
}
