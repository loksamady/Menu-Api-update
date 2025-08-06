package kh.com.csx.exception;

    import kh.com.csx.constant.Constant;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;

    @Getter
    @EqualsAndHashCode(callSuper = true)
    @Data
    public class APIException extends RuntimeException {
        private final int responseCode;

        public APIException(String message, int responseCode) {
            super(message);
            this.responseCode = responseCode;
        }

        public static APIException badRequest() {
            return new APIException("Bad Request", Constant.HTTP_STATUS_BAD_REQUEST);
        }

        public static APIException badRequest(String message) {
            return new APIException(message, Constant.HTTP_STATUS_BAD_REQUEST);
        }

        public static APIException notFound() {
            return new APIException("Not Found", Constant.HTTP_STATUS_NOT_FOUND);
        }

        public static APIException notFound(String message) {
            return new APIException(message, Constant.HTTP_STATUS_NOT_FOUND);
        }

        public static APIException conflict() {
            return new APIException("Conflict", Constant.HTTP_STATUS_CONFLICT);
        }

        public static APIException conflict(String message) {
            return new APIException(message, Constant.HTTP_STATUS_CONFLICT);
        }

        public static APIException unprocessableContent() {
            return new APIException("Unprocessable Content", Constant.HTTP_STATUS_UNPROCESSABLE_CONTENT);
        }

        public static APIException unprocessableContent(String message) {
            return new APIException(message, Constant.HTTP_STATUS_UNPROCESSABLE_CONTENT);
        }

        public static APIException internalServerError() {
            return new APIException("Internal Server Error", Constant.HTTP_STATUS_INTERNAL_SERVER_ERROR);
        }

        public static APIException internalServerError(String message) {
            return new APIException(message, Constant.HTTP_STATUS_INTERNAL_SERVER_ERROR);
        }
    }