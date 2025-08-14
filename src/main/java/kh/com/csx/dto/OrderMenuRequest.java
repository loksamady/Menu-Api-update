package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderMenuRequest {

    @NotNull(message = "Menu ID is required")
    private Integer menuId;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("discount")
    private BigDecimal discount = BigDecimal.ZERO; // Discount applied to the menu item in the
}
