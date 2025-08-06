package kh.com.csx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchant_themes")
public class MerchantTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "merchant_id")
    private Integer merchantId;

    @Column(name = "primary_color")
    private String primary;

    @Column(name = "primary_light_color")
    private String primaryLight;

    @Column(name = "primary_dark_color")
    private String primaryDark;

    @Column(name = "secondary_color")
    private String secondary;

    @Column(name = "secondary_light_color")
    private String secondaryLight;

    @Column(name = "secondary_dark_color")
    private String secondaryDark;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

//    @Column(name = "deleted_at")
//    private Date deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}


