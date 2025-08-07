package kh.com.csx.dto;

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
    private String menuName;
    private Integer quantity;
    private BigDecimal priceAtOrder;
}
