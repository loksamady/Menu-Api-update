package kh.com.csx.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class VendorRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private Integer period;
    private Double price;
    private Double discount;
    private Integer merchantLimit;
    private Boolean status;
    private String username;
    private String userPassword;
    private Boolean isBaseOwner;
    private List<Integer> userRoles;

    public VendorRequest() {
        this.isBaseOwner = true;
    }
}
