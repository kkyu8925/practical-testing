package sample.cafekiosk.spring.api

import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun bindException(e: MethodArgumentNotValidException): ApiResponse<String> {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST, e.bindingResult.allErrors.first().defaultMessage ?: "error", "data"
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(e: HttpMessageNotReadableException): ApiResponse<String> {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST, e.message ?: "error", "data"
        )
    }
}

