package study.community.board.global.apiPayload.code;

import study.community.board.global.apiPayload.code.dto.ErrorReasonDTO;

public interface BaseErrorCode {
    ErrorReasonDTO getErrorReason();

    ErrorReasonDTO getErrorReasonHttpStatus();
}
