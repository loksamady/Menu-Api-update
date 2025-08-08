package kh.com.csx.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderMenuResponse {
    private Integer menuId;
    private String menuName;
    private Integer quantity;
    private Long orderId;
//    private BigDecimal priceAtOrder;
}
