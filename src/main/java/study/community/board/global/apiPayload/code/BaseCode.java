package study.community.board.global.apiPayload.code;

import study.community.board.global.apiPayload.code.dto.ReasonDTO;

public interface BaseCode {
    ReasonDTO getReason();
    ReasonDTO getReasonHttpStatus();

}
