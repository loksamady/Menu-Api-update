// src/main/java/kh/com/csx/dto/OrderDetailResponse.java
    package kh.com.csx.dto;

    import lombok.*;
    import java.math.BigDecimal;
    import java.util.Date;
    import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class OrderDetailResponse {
        private Long id;
        private Long orderId;
        private String customerName;
        private String customerPhone;
        private String customerAddress;
//        private String note;
        private BigDecimal totalPrice;
//        private String status;
        private Date createdAt;
//        private Date updatedAt;
        private Long customerId;
        private List<OrderMenuResponse> orderMenuRequests; // <-- Change type here
    }