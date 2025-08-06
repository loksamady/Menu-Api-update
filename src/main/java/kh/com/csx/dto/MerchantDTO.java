package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kh.com.csx.entity.Merchant;
import kh.com.csx.entity.MerchantFile;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class MerchantDTO {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String primaryPhone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secondaryPhone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String titleEn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String titleKh;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String titleCn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subtitleEn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subtitleKh;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subtitleCn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descriptionEn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descriptionKh;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descriptionCn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logoUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String openTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String closeTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String slug;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String telegram;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String facebook;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String instagram;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tiktok;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer vendorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean active;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MerchantFile> banners;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;

    public static MerchantDTO toListDTO(Merchant entity) {
        return MerchantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .primaryPhone(entity.getPrimaryPhone())
                .secondaryPhone(entity.getSecondaryPhone())
                .address(entity.getAddress())
                .vendorId(entity.getVendorId())
                .logo(entity.getLogo())
                .slug(entity.getSlug())
                .active(entity.getActive() == 1)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static MerchantDTO toDetailDTO(Merchant entity, String baseUrl) {
        String logoUrl = entity.getLogo() != null
                ? String.format("%s/files/logo/%s", baseUrl, entity.getLogo())
                : null;

        return MerchantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .primaryPhone(entity.getPrimaryPhone())
                .secondaryPhone(entity.getSecondaryPhone())
                .address(entity.getAddress())
                .vendorId(entity.getVendorId())
                .titleEn(entity.getTitleEn())
                .titleKh(entity.getTitleKh())
                .titleCn(entity.getTitleCn())
                .subtitleEn(entity.getSubtitleEn())
                .subtitleKh(entity.getSubtitleKh())
                .subtitleCn(entity.getSubtitleCn())
                .descriptionEn(entity.getDescriptionEn())
                .descriptionKh(entity.getDescriptionKh())
                .descriptionCn(entity.getDescriptionCn())
                .location(entity.getLocation())
                .logo(entity.getLogo())
                .logoUrl(logoUrl)
                .openTime(entity.getOpenTime())
                .closeTime(entity.getCloseTime())
                .slug(entity.getSlug())
                .telegram(entity.getTelegram())
                .facebook(entity.getFacebook())
                .instagram(entity.getInstagram())
                .tiktok(entity.getTiktok())
                .active(entity.getActive() == 1)
                .banners(entity.getBanners())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static MerchantDTO toOptionDTO(Merchant entity) {
        return MerchantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .vendorId(entity.getVendorId())
                .build();
    }

    public static MerchantDTO toAuthDTO(Merchant entity) {
        return MerchantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .vendorId(entity.getVendorId())
                .active(entity.getActive() == 1)
                .build();
    }

    public static MerchantDTO toWebsiteDTO(Merchant entity, String baseUrl) {
        String logoUrl = entity.getLogo() != null
                ? String.format("%s/files/logo/%s", baseUrl, entity.getLogo())
                : null;

        return MerchantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .primaryPhone(entity.getPrimaryPhone())
                .secondaryPhone(entity.getSecondaryPhone())
                .address(entity.getAddress())
                .vendorId(entity.getVendorId())
                .titleEn(entity.getTitleEn())
                .titleKh(entity.getTitleKh())
                .titleCn(entity.getTitleCn())
                .subtitleEn(entity.getSubtitleEn())
                .subtitleKh(entity.getSubtitleKh())
                .subtitleCn(entity.getSubtitleCn())
                .descriptionEn(entity.getDescriptionEn())
                .descriptionKh(entity.getDescriptionKh())
                .descriptionCn(entity.getDescriptionCn())
                .location(entity.getLocation())
                .logo(entity.getLogo())
                .banners(entity.getBanners())
                .logoUrl(logoUrl)
                .openTime(entity.getOpenTime())
                .closeTime(entity.getCloseTime())
                .slug(entity.getSlug())
                .telegram(entity.getTelegram())
                .facebook(entity.getFacebook())
                .instagram(entity.getInstagram())
                .tiktok(entity.getTiktok())
                .active(entity.getActive() == 1)
                .build();
    }
}
