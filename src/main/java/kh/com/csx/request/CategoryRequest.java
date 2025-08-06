package kh.com.csx.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CategoryRequest {
    @NotBlank(message = "NameEn is required")
    private String nameEn;
    @NotBlank(message = "NameKh is required")
    private String nameKh;
    @NotBlank(message = "NameCn is required")
    private String nameCn;
    private Integer status;
    private Integer displayOrder;
    private Integer merchantId;
}
