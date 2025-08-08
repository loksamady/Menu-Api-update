package kh.com.csx.repository;

import kh.com.csx.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT order FROM Order order JOIN FETCH order.customer WHERE order.customer.id = :customerId")
    Optional<OrderRepository> findByCustomerId(Long customerId);

    @Query("SELECT DISTINCT order FROM Order order LEFT JOIN FETCH order.orderMenus ordermenus LEFT JOIN FETCH ordermenus.menu")
    List<Order> findAllWithOrderMenus();

    @Query("SELECT order FROM Order order LEFT JOIN FETCH order.orderMenus ordermenus LEFT JOIN FETCH ordermenus.menu WHERE order.id = :orderId")
    Optional<Order> findByIdWithOrderMenus(@Param("orderId") Long orderId);
}
