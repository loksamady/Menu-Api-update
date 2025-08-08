package kh.com.csx.entity;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.Date;
    import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
    @Table(name = "customers")
    public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, unique = true)
        private Long id;

        @Column(name = "username", length = 255, unique = false)
        private String username;

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "address")
        private String address;

        @Column(name = "telegramId")
        private String telegramId;

        @Column(name = "telegramUsername")
        private String telegramUsername;

        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        private Date createdAt;

        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        private Date updatedAt;

        @Column(name = "deleted_at")
        @Temporal(TemporalType.TIMESTAMP)
        private Date deletedAt;

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Order> orders;
    }