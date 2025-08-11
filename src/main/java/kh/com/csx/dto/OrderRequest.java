// src/main/java/kh/com/csx/dto/OrderRequest.java
    package kh.com.csx.dto;

    import com.fasterxml.jackson.annotation.JsonProperty;
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

        @JsonProperty("customer_name")
        private String customerName;

        @JsonProperty("customer_phone")
        private String customerPhone;

        @JsonProperty("customer_address")
        private String customerAddress;

        @JsonProperty("payment_status")
        private String paymentStatus; // Payment status as a string

        @JsonProperty("note")
        private String note; // Optional note for the order

        private List<OrderMenuRequest> orderMenuRequests;

        @JsonProperty("total_price")
        private BigDecimal totalPrice;

        @JsonProperty("created_at")
        private Date createdAt;
    }