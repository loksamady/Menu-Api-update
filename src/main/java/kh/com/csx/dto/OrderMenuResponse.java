package kh.com.csx.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderMenuResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private Integer quantity;
    private Long orderId;
}
