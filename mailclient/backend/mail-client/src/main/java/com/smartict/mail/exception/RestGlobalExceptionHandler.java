/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.mail.exception;

import java.lang.reflect.Type;

import com.smartict.mail.constant.exceptions.ServiceException;
import com.smartict.mail.constant.exceptions.ValidationException;
import com.smartict.mail.constant.messages.EnumExceptionMessages;
import com.smartict.mail.dto.response.ExceptionHandlerDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

/**
 * Tüm isteklerin hata ile karşılan durumlarda bu sınıf aracılığıyla hatalar yakalanır ve uygun cevaplar dönülür. İşlemlerin içerisinde exceptionlar bu sınıf
 * ile yakalanır ve uygun cevaplar isteklerin cevabında dönülür ve istek status degeri EXPECTATION_FAILED olarak veya INTERNAL_SERVER_ERROR olarak dönülür.
 */
@ControllerAdvice
public class RestGlobalExceptionHandler extends RequestBodyAdviceAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestGlobalExceptionHandler.class);
    // TODO AuthenticationLogService işlemleri alınacaktır.
    // private final AuthenticationLogService authenticationLogService;
    private Object bodyObj;

    // public RestGlobalExceptionHandler(AuthenticationLogService authenticationLogService) {
    // this.authenticationLogService = authenticationLogService;
    // }

    @ExceptionHandler(value = {
        Exception.class
    })
    protected ResponseEntity<Object> handleConflict(Exception e, WebRequest request) {
        LOGGER.error("Global Exception Handler:", e);

        ExceptionHandlerDto exceptionHandlerDto = new ExceptionHandlerDto();
        exceptionHandlerDto.setMessage(e.getMessage());
        exceptionHandlerDto.setLocalizedMessage(e.getLocalizedMessage());

        if (e instanceof ValidationException || e instanceof ServiceException) {
            ServiceException se = (ServiceException) e;
            exceptionHandlerDto.setWarning(se.getWarning());
            return new ResponseEntity<>(exceptionHandlerDto, HttpStatus.EXPECTATION_FAILED);
        } else if (e instanceof AccessDeniedException) {
            // authenticationLogService.saveAuthenticationLog(((ServletWebRequest) request).getRequest(), e, bodyObj);
            return new ResponseEntity<>(exceptionHandlerDto, HttpStatus.UNAUTHORIZED);
        } else {
            Throwable result = new ServiceException(
                EnumExceptionMessages.UNEXPECTED_ERROR_OCCURRED.getLanguageKey(),
                EnumExceptionMessages.UNEXPECTED_ERROR_OCCURRED.getLanguageValue()
            );
            exceptionHandlerDto.setMessage(result.getMessage());
            exceptionHandlerDto.setLocalizedMessage(result.getLocalizedMessage());
            return new ResponseEntity<>(exceptionHandlerDto, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(
        Object body,
        HttpInputMessage inputMessage,
        MethodParameter parameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) {
        bodyObj = body;
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}
