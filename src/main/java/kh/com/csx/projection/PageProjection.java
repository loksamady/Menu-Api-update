package kh.com.csx.projection;


public interface PageProjection {

    Integer getId();

    Integer getParentId();

    String getTitleEn();

    String getTitleKh();

    String getUrl();

    String getPath();

    String getPageComponent();

    String getParameter();

    Integer getDisplayOrder();

    Integer getStatus();

}
