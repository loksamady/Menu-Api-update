package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kh.com.csx.entity.Category;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class AuthDTO {
    private Integer categoryId;
    private String nameEn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nameKh;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nameCn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer displayOrder;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer merchantId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;

    public static AuthDTO toListDTO(Category entity) {
        return AuthDTO.builder()
                .categoryId(entity.getCategoryId())
                .nameEn(entity.getNameEn())
                .nameKh(entity.getNameKh())
                .nameCn(entity.getNameCn())
                .displayOrder(entity.getDisplayOrder())
                .merchantId(entity.getMerchantId())
                .status(entity.getStatus() == 1)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AuthDTO toDetailDTO(Category entity) {
        return AuthDTO.builder()
                .categoryId(entity.getCategoryId())
                .nameEn(entity.getNameEn())
                .nameKh(entity.getNameKh())
                .nameCn(entity.getNameCn())
                .displayOrder(entity.getDisplayOrder())
                .merchantId(entity.getMerchantId())
                .status(entity.getStatus() == 1)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AuthDTO toOptionDTO(Category entity) {
        return AuthDTO.builder()
                .categoryId(entity.getCategoryId())
                .nameEn(entity.getNameEn())
                .merchantId(entity.getMerchantId())
                .build();
    }

    public static AuthDTO toWebsiteDTO(Category entity) {
        return AuthDTO.builder()
                .categoryId(entity.getCategoryId())
                .nameEn(entity.getNameEn())
                .nameKh(entity.getNameKh())
                .nameCn(entity.getNameCn())
                .displayOrder(entity.getDisplayOrder())
                .merchantId(entity.getMerchantId())
                .build();
    }
}
