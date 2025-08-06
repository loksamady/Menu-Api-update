package kh.com.csx.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kh.com.csx.constant.Constant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.stream.StreamSupport;

@Getter
public class PageableResponse extends BaseResponse implements Response {
    @Setter
    private String message = null;
    @Setter
    private Long totalRecords = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long currentRecords = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPages = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer currentPage = null;
    private Object data = null;

    public PageableResponse() {
        super(Constant.HTTP_STATUS_OK);
    }

    public PageableResponse(Page<?> page) {
        super(Constant.HTTP_STATUS_OK);
        this.data = page.getContent();
        this.totalRecords = page.getTotalElements();
        this.currentRecords = this.updateTotalRecords(this.data);
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
    }

    public static PageableResponse build(Page<?> page) {
        return new PageableResponse(page);
    }

    private Long updateTotalRecords(Object data) {
        if (data instanceof Iterable<?>) {
            return StreamSupport.stream(((Iterable<?>) data).spliterator(), false).count();
        } else if (data != null) {
            return 1L;
        }
        return null;
    }
}
