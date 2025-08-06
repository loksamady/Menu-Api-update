package kh.com.csx.repository;

import kh.com.csx.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT order FROM Order order JOIN FETCH order.customer WHERE order.customer.id = :customerId")
    Optional<OrderRepository> findByCustomerId(Long customerId);
}
