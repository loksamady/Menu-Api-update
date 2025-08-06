package kh.com.csx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table (name = "customers")
public class Customer {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "username", unique = true, nullable = false)

    private String username;

    private String phoneNumber;

    private String address;

    private String telegramId;

    private String telegramUsername;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Order> orders; // List of orders associated with the customer
}
