package kh.com.csx.request.subscriptionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SubscriptionTypeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Period is required")
    private Integer period;

    @NotBlank(message = "Period Unit is required")
    private String periodUnit;
}
