package kh.com.csx.request;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MerchantThemeRequest {
    private Integer merchantId;

    private String primary;

    private String primaryLight;

    private String primaryDark;

    private String secondary;

    private String secondaryLight;

    private String secondaryDark;
}
