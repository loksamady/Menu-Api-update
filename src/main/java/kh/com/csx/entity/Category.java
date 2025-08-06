package kh.com.csx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Where(clause = "deleted_at IS NULL")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-increment in most SQL databases
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name_en", nullable = false) // Ensure name is unique
    private String nameEn;

    @Column(name = "name_kh", nullable = false) // Ensure name is unique
    private String nameKh;

    @Column(name = "name_cn", nullable = false) // Ensure name is unique
    private String nameCn;

    @Column(name = "status")
    private int status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "merchant_id")
    private Integer merchantId;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Menu> menus;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}


