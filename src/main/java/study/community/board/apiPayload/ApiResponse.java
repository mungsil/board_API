package study.community.board.apiPayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import study.community.board.apiPayload.code.BaseCode;
import study.community.board.apiPayload.code.SuccessStatus;

@Builder
@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result) {
        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
    }

    //Exception Advice에서 사용
    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return new ApiResponse(false, code, message, result);
    }

    //isSucess, success 동시 반환 문제 해결
    public boolean getIsSuccess() {
        return isSuccess;
    }

}
