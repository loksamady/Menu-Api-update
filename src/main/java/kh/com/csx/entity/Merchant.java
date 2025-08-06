package kh.com.csx.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants")
@Where(clause = "deleted_at IS NULL")
public class Merchant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "primary_phone", unique = true)
    private String primaryPhone;

    @Column(name = "secondary_phone")
    private String secondaryPhone;

    @Column(name = "address")
    private String address;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_kh")
    private String titleKh;

    @Column(name = "title_cn")
    private String titleCn;

    @Column(name = "subtitle_en")
    private String subtitleEn;

    @Column(name = "subtitle_kh")
    private String subtitleKh;

    @Column(name = "subtitle_cn")
    private String subtitleCn;

    @Column(name = "description_en", columnDefinition = "LONGTEXT")
    private String descriptionEn;

    @Column(name = "description_kh", columnDefinition = "LONGTEXT")
    private String descriptionKh;

    @Column(name = "description_cn", columnDefinition = "LONGTEXT")
    private String descriptionCn;

    @Column(name = "location")
    private String location;

    @Column(name = "logo")
    private String logo;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "tiktok")
    private String tiktok;

    @Column(name = "active")
    private Integer active;

    @Column(name = "vendor_id")
    private Integer vendorId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "merchant", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "merchant_id") // adds a foreign key to merchant_files
    @ToString.Exclude
    private List<MerchantFile> banners = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", insertable = false, updatable = false)
    @JsonBackReference
    @ToString.Exclude
    private Vendor vendor;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
