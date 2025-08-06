package kh.com.csx.constant;

public class Constant {
    public static final String JWT_AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_AUTHORIZATION_HEADER_STARTER = "Bearer ";

    // HTTP STATUS CODE
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_CONFLICT = 409;
    public static final int HTTP_STATUS_UNPROCESSABLE_CONTENT = 422;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;

    // PATH
    public static final String WEBSITE_CONTEXT_PATH = "/website";
    public static final String ADMIN_CONTEXT_PATH = "/admin";
    public static final String API_V1 = "/api/v1";
    public static final String API_V1_WEBSITE = API_V1 + WEBSITE_CONTEXT_PATH;
    public static final String API_V1_ADMIN = API_V1 + ADMIN_CONTEXT_PATH;

    // PAGE
    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_PAGE_SIZE = "12";
}
