// src/main/java/kh/com/csx/dto/OrderRequest.java
    package kh.com.csx.dto;

    import lombok.*;
    import java.math.BigDecimal;
    import java.util.Date;
    import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class OrderRequest {
        private Long customerId;
        private String customerName;
        private List<OrderMenuRequest> orderMenuRequests;
        private BigDecimal totalPrice;
        private Date createdAt;
        private String note;
    }