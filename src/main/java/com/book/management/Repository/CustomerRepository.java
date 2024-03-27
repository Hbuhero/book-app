package com.book.management.Repository;

import com.book.management.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer WHERE username = ?1", nativeQuery = true)
    Optional<Customer> findByUsername(String username);

    @Query(value = "SELECT EXISTS (SELECT * FROM customer WHERE LOWER(username) = LOWER(?1))", nativeQuery = true)
    boolean existsByUsername(String username);
}
