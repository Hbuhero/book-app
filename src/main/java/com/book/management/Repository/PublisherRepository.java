package com.book.management.Repository;

import com.book.management.Model.Publisher;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query(value = "SELECT * FROM publisher WHERE username = ?1", nativeQuery = true)
    Optional<Publisher> findByUsername(String username);

    @Query(value = "SELECT id FROM publisher WHERE name = ?1", nativeQuery = true)
    Long findIdByName(String publisher);

    @Query(value = "SELECT EXISTS (SELECT * FROM publisher WHERE LOWER(username) = LOWER(?1))", nativeQuery = true)
    boolean existsByUsername(String username);
}
