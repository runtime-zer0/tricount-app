package runtimezero.tricount.util;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import runtimezero.tricount.enums.TricountErrorCode;
import runtimezero.tricount.dto.ApiResponse;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class TricountApiExceptionHandler {
    // 커스텀 예외 하나씩 정의해서 추가
    @ExceptionHandler(TricountApiException.class)
    public ApiResponse<Object> sendtimeExceptionHandler(TricountApiException e, HttpServletResponse response) {
        TricountErrorCode errorCode = e.getErrorCode();
        response.setStatus(errorCode.getStatus());
        log.warn("sendtimemExceptionHandler", e);

        return new ApiResponse<>().fail(errorCode.getCode(), e.getMessage());
    }
}
