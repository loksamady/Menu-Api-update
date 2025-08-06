package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kh.com.csx.entity.Vendor;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class VendorDTO {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer period;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer merchantLimit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDTO> users;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MerchantDTO> merchants;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;

    public static VendorDTO toListDTO(Vendor entity) {
        return VendorDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .period(entity.getPeriod())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .merchantLimit(entity.getMerchantLimit())
                .status(entity.getStatus() == 1)
                .users(entity.getUsers().stream().map(UserDTO::toRelationDTO).toList())
                .merchants(entity.getMerchants().stream().map(MerchantDTO::toListDTO).toList())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static VendorDTO toDetailDTO(Vendor entity) {
        return VendorDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .period(entity.getPeriod())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .merchantLimit(entity.getMerchantLimit())
                .status(entity.getStatus() == 1)
                .users(entity.getUsers().stream().map(UserDTO::toRelationDTO).toList())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static VendorDTO toOptionDTO(Vendor entity) {
        return VendorDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static VendorDTO toAuthDTO(Vendor entity) {
        return VendorDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .merchantLimit(entity.getMerchantLimit())
                .status(entity.getStatus() == 1)
                .build();
    }
}
