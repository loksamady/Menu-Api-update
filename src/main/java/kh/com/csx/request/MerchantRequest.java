package kh.com.csx.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MerchantRequest {
    private final Boolean active;

    @NotBlank(message = "name is required")
    private String name;

    private String primaryPhone;

    private String secondaryPhone;

    private String address;

    private String titleEn;

    private String titleKh;

    private String titleCn;

    private String subtitleEn;

    private String subtitleKh;

    private String subtitleCn;

    private String descriptionEn;

    private String descriptionKh;

    private String descriptionCn;

    private String location;

    private String openTime;

    private String closeTime;

    private String slug;

    private String telegram;

    private String facebook;

    private String instagram;

    private String tiktok;

    private Boolean isLogoDeleted;

    private MultipartFile logo;

    @Size(max = 5, message = "Banners can only be up to 5")
    private List<MultipartFile> banners;

    // Theme Data
    private Integer merchantId;

    private String primary;

    private String primaryLight;

    private String primaryDark;

    private String secondary;

    private String secondaryLight;

    private String secondaryDark;
}
