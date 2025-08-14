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

    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne Order order;

    @JoinColumn(name = "menu_id", nullable = false)
    @ManyToOne Menu menu;

    @Column (name = "menu_name")
    private String menuName; // Name of the menu item

    @Column(name = "quantity")
    private int quantity; // Quantity of the menu item in the order

    @Column(name = "price")
    private BigDecimal price; // Price of the menu item at the time of the order

    @Column(name = "discount")
    private BigDecimal discount; // Discount applied to the menu item in the order

    @Column (name = "total_price")
    private BigDecimal totalPrice; // Total price for the menu item in the order (quantity * price - discount)
}
