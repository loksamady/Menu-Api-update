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
public class OrderDetailResponse {
    private Long orderId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String note;
    private BigDecimal totalPrice;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String customerId; // Unique identifier for the customer
    List<OrderMenuRequest> orderMenuRequests; // List of order menu items associated with the order

}
