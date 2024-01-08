package study.community.board.apiPayload.code;

import study.community.board.apiPayload.code.dto.ReasonDTO;

public interface BaseCode {
    ReasonDTO getReason();
    ReasonDTO getReasonHttpStatus();

}
