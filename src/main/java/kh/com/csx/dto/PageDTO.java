package kh.com.csx.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDTO {
    private Integer id;
    private Integer parentId;
    private String titleEn;
    private String titleKh;
    private String url;
    private String path;
    private String pageComponent;
    private Integer displayOrder;
    private String parameter;
    private Integer status;
    private List<PageDTO> childPages;
}
