package kh.com.csx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "order_menu")
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne Order order;
    @ManyToOne Menu menu;
    private int quantity; // Quantity of the menu item in the order
    private double price; // Price of the menu item at the time of the order
    private double discount; // Discount applied to the menu item in the order
    private double totalPrice; // Total price for the menu item in the order (quantity * price - discount)
    private BigDecimal priceAtOrder;
}
