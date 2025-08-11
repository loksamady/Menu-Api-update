// src/main/java/kh/com/csx/dto/OrderDetailResponse.java
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
    public class OrderDetailResponse {
//        private Long id;
        private Long orderId;

        @JsonProperty("customer_name")
        private String customerName;
        @JsonProperty("customer_phone")
        private String customerPhone;
        @JsonProperty("customer_address")
        private String customerAddress;
//        private String note;
        @JsonProperty("total_amount")
        private BigDecimal total_amount;
//        private String status;
        @JsonProperty("created_at")
        private Date createdAt;

        @JsonProperty("updated_at")
        private Date updatedAt;

        private Long customerId;
        private List<OrderMenuResponse> orderMenuRequests; // <-- Change type here
    }