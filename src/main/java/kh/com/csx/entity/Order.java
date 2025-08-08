package kh.com.csx.entity;

import jakarta.persistence.*;
import kh.com.csx.entity.enums.PaymentStatus;
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

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING) // Store enum as a string in the database
    private PaymentStatus paymentStatus; // Enum for payment status

    @Column(name="total_price")
    private BigDecimal totalPrice;

    @Column(name = "customer_phone")
    private String customerPhone; // Customer's phone number

    @Column(name = "customer_address")
    private String customerAddress; // Customer's address

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP) // Store as a timestamp in the database
    private Date createdAt; // Timestamp for when the order was created

    @Column(name= "updated_at")
    @Temporal(TemporalType.TIMESTAMP) // Store as a timestamp in the database
    private Date updatedAt; // Timestamp for when the order was last updated

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    // If you have another field mapping the same column, ensure it is properly annotated:
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<OrderMenu> orderMenus; // List of order menu items associated with
}
