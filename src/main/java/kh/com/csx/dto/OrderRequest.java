package kh.com.csx.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderRequest {
    private Long customerId;
    List<OrderMenuRequest> orderMenuRequests;
    private BigDecimal totalPrice;
    private Date createdAt;
    private String note;
}
