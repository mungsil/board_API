package study.community.board.apiPayload.code;

import study.community.board.apiPayload.code.dto.ErrorReasonDTO;

public interface BaseErrorCode {
    ErrorReasonDTO getErrorReason();

    ErrorReasonDTO getErrorReasonHttpStatus();
}
