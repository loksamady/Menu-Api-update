package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kh.com.csx.entity.SubscriptionType;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class SubscriptionTypeDTO {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer period;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String periodUnit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;

    public static SubscriptionTypeDTO toListDTO(SubscriptionType entity) {
        return SubscriptionTypeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .period(entity.getPeriod())
                .periodUnit(entity.getPeriodUnit())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static SubscriptionTypeDTO toOptionDTO(SubscriptionType entity) {
        return SubscriptionTypeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .period(entity.getPeriod())
                .periodUnit(entity.getPeriodUnit())
                .build();
    }
}
