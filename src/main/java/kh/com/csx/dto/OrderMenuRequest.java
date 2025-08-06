package kh.com.csx.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderMenuRequest {
    private Long menuId;
    private String menuName;
    private String menuImage;
    private Integer quantity;
    private Double price;
    private String note;
}
