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

    @Column(name = "menu_id", insertable = false, updatable = false)
    private Integer menuId; // Foreign key to Menu entity

    @Column (name = "menu_name")
    private String menuName; // Name of the menu item

    private int quantity; // Quantity of the menu item in the order
    private BigDecimal price; // Price of the menu item at the time of the order
    private BigDecimal discount; // Discount applied to the menu item in the order

    @Column (name = "total_price")
    private BigDecimal totalPrice; // Total price for the menu item in the order (quantity * price - discount)
    private BigDecimal priceAtOrder;
}
