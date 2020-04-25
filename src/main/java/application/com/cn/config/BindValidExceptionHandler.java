package application.com.cn.config;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;

@ControllerAdvice
@Component
public class BindValidExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public void handlerConstraintViolationException(HttpServletRequest httpServletRequest, Exception e) {
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
        String messageTemplate = ((ConstraintViolationException) e).getConstraintViolations().iterator().next().getMessageTemplate();
    }

    @ExceptionHandler(BindException.class)
    public void handlerBindException(HttpServletRequest httpServletRequest, Exception e) {
        BindException bindException = (BindException) e;
        List<ObjectError> objectErrors = ((BindException) e).getBindingResult().getAllErrors();
        Iterator<ObjectError> iterator = objectErrors.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getDefaultMessage());
        }
    }

}
