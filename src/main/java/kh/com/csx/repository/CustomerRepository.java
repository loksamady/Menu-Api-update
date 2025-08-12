package kh.com.csx.repository;

import kh.com.csx.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
    Optional<Customer> findById(Long id);

    // Additional query methods can be defined here if needed
    // For example, findByEmail, findByLastName, etc.
}
