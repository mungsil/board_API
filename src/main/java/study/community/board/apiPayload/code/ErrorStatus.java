package study.community.board.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.community.board.apiPayload.code.dto.ErrorReasonDTO;

//enum이 inferface를 상속 받을 수 있는 이유??
//enum은 일종의 static이라 스프링 빈으로 등록할 필요가 없나?

@AllArgsConstructor
@Getter
public enum ErrorStatus implements BaseErrorCode {
    // member error
    MEMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "MEMBER4001", "멤버 없음"),
    COMMENT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMENT4001", "댓글 없음"),
    POST_BAD_REQUEST(HttpStatus.BAD_REQUEST, "POST4001", "게시글 없음");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getErrorReason() {
        return ErrorReasonDTO.builder()
                .code(code)
                .message(message)
                .isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .isSuccess(false).build();
    }
}
