package kh.com.csx.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus")
@Where(clause = "deleted_at IS NULL")
@ToString
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-increment in most SQL databases
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "code")
    private String code;

    @Column(name = "name_en", nullable = false) // Ensure name is unique
    private String nameEn;

    @Column(name = "name_kh", nullable = false) // Ensure name is unique
    private String nameKh;

    @Column(name = "name_cn", nullable = false) // Ensure name is unique
    private String nameCn;

    @Lob
    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEn;

    @Lob
    @Column(name = "description_kh", columnDefinition = "TEXT")
    private String descriptionKh;

    @Lob
    @Column(name = "description_cn", columnDefinition = "TEXT")
    private String descriptionCn;

    @Column(name = "price")
    private double price;

    @Column(name = "price_kh")
    private double priceKh;

    @Column(name = "discount")
    private double discount;

    @Column(name = "status")
    private int status;

    @Column(name = "is_hot")
    private boolean isHot;

    @Column(name = "merchant_id")
    private Integer merchantId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_categories",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    private List<Category> categories = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id") // adds a foreign key to menu_files
    @ToString.Exclude
    private List<MenuFile> files = new ArrayList<>();

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}