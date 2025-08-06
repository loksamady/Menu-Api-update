package kh.com.csx.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageRequest {
    @Min(value = 0, message = "parentId must be a number")
    private String parentId;

    @NotNull(message = "titleEn is required")
    @NotBlank(message = "titleEn cannot be blanked")
    private String titleEn;

    @NotNull(message = "titleKh is required")
    @NotBlank(message = "titleKH cannot be blanked")
    private String titleKh;

    private String url;

    private String descriptionEn;

    private String descriptionKh;

    @Min(value = 0, message = "order must be a number")
    private String displayOrder;

    @Min(value = 0, message = "status must be a number")
    private String status;

    public PageRequest() {
        // Default value
        this.status = "1";
        this.displayOrder = "0";
    }
}
