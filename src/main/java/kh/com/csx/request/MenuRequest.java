package kh.com.csx.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MenuRequest {
    @NotBlank(message = "NameEn is required")
    private String nameEn;

    @NotBlank(message = "NameKh is required")
    private String nameKh;

    @NotBlank(message = "NameCn is required")
    private String nameCn;

    private String code;

    private String descriptionEn;

    private String descriptionKh;

    private String descriptionCn;

    private double price = 0.0;

    private double priceKh = 0.0;

    private double discount = 0.0;

    private int status = 1;

    private boolean hot;

    private List<Integer> categories;

    @Size(max = 6, message = "Attach files can only be up to 6")
    private List<MultipartFile> files;

    private Long setMainFileId;
    
    private Integer merchantId;
}
