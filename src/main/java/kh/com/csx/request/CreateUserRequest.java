package kh.com.csx.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull(message = "username can't be empty!")
    private String username;

    @NotNull(message = "password can't be empty!")
    private String password;

    private Integer merchantId;

    private Integer vendorId;

    private Boolean isBaseOwner;

    private List<Integer> roles;

    public CreateUserRequest() {
        this.isBaseOwner = false;
    }
}

