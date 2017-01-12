package rs.ac.bg.fon.silab.dms.rest.model;


public class ApiResponse {
    private boolean success;
    private Object content;
    private String errorMessage;

    private ApiResponse() {
        
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getContent() {
        return content;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static ApiResponse createErrorResponse(String errorMessage) {
        ApiResponse response = new ApiResponse();
        response.success = false;
        response.content = null;
        response.errorMessage = errorMessage;
        return response;
    }

    public static ApiResponse createSuccessResponse(Object responseContent) {
        ApiResponse response = new ApiResponse();
        response.success = true;
        response.content = responseContent;
        response.errorMessage = "";
        return response;
    }
}
