package kh.com.csx.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

public interface CommonProjection {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getId();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getNo();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getTitle();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getSource();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getContent();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getUploaded();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getDate();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getClassification();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean getIsNotice();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean getIsNew();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getCompany();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getContentRaw();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getPeriod();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getWriter();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getLink();
}
