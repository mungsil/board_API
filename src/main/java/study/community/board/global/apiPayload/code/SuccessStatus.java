package study.community.board.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.community.board.global.apiPayload.code.dto.ReasonDTO;

@AllArgsConstructor
@Getter
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK,"COMMON200","성공! ദ്ദി˶˙ᵕ˙˶ )");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .code(code)
                .message(message)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .code(code)
                .message(message)
                .isSuccess(true)
                .build();
    }
}
