package kh.com.csx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kh.com.csx.entity.Role;
import kh.com.csx.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserDTO {
    private Long userId;
    private String username;
    private Integer merchantId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer vendorId;
    private Boolean status;
    private Integer isBaseOwner;
    private String referralId;
    private VendorDTO vendor;
    private Set<Role> roles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MerchantDTO> merchants;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<? extends GrantedAuthority> authorities;
    private Date createdAt;
    private Date updatedAt;

    public static UserDTO toListDTO(User entity) {
        return UserDTO.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .merchantId(entity.getMerchantId())
                .vendorId(entity.getVendorId())
                .isBaseOwner(entity.getIsBaseOwner())
                .referralId(entity.getReferralId())
                .status(entity.getStatus() == 1)
                .roles(entity.getRoles())
                .authorities(entity.getAuthorities())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static UserDTO toRelationDTO(User entity) {
        return UserDTO.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .merchantId(entity.getMerchantId())
                .vendorId(entity.getVendorId())
                .isBaseOwner(entity.getIsBaseOwner())
                .referralId(entity.getReferralId())
                .status(entity.getStatus() == 1)
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
