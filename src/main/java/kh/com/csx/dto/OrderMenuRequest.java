package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderMenuRequest {
    private Long menuId;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("order_id")
    private Long orderId;

}
