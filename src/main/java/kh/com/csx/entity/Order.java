package kh.com.csx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use
    private Long id;
    private String total_amount;
    private String status; // e.g., "pending", "completed", "cancelled"
    private String payment_method; // e.g., "cash", "credit_card", "mobile_payment"
    private BigDecimal totalPrice;
    private Date created_at; // Timestamp for when the order was created
    private Date updated_at; // Timestamp for when the order was last updated
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    // If you have another field mapping the same column, ensure it is properly annotated:
    @Column(name = "customer_id")
    private Long customerId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<OrderMenu> orderMenus; // List of order menu items associated with
}
