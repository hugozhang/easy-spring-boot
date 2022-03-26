package com.juma.template.advice;

import com.juma.template.common.JsonResult;
import com.juma.template.common.ValidFieldError;
import com.juma.template.exception.BizException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @Valid 与 @Validated 的应用场景
 * https://langinteger.github.io/2019/09/13/java-bean-validation/
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * form表单形式的参数验证
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    public JsonResult validParameterWrap(BindException ex) {
        List<FieldError> errors= ex.getBindingResult().getFieldErrors();
        JsonResult result = buildValidFieldError(errors);
        return result;
    }

    /**
     * json body形式的参数验证
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public JsonResult validParameterWrap2(MethodArgumentNotValidException ex) {
        List<FieldError> errors= ex.getBindingResult().getFieldErrors();
        JsonResult result = buildValidFieldError(errors);
        return result;
    }

    /**
     * controller 转换单一属性检验失败 比如：int,string,list
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public JsonResult validParameterWrap3(ConstraintViolationException ex) {
        List<ValidFieldError> results = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for ( ConstraintViolation constraintViolation : constraintViolations ) {
            ValidFieldError validFieldError = new ValidFieldError();
            validFieldError.setField(constraintViolation.getPropertyPath().toString());
            validFieldError.setMessage(constraintViolation.getMessage());
            results.add(validFieldError);
        }
        JsonResult result = new JsonResult();
        result.setCode(400);
        result.setMessage("参数检验失败");
        result.setData(results);
        return result;
    }

    private JsonResult buildValidFieldError(List<FieldError> errors) {
        List<ValidFieldError> results = new ArrayList<>();
        for(FieldError fieldError : errors) {
            ValidFieldError validFieldError = new ValidFieldError();
            validFieldError.setField(fieldError.getField());
            validFieldError.setMessage(fieldError.getDefaultMessage());
            results.add(validFieldError);
        }
        JsonResult result = new JsonResult();
        result.setCode(400);
        result.setMessage("参数检验失败");
        result.setData(results);
        return result;
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public JsonResult businessException(BizException ex) {
        JsonResult result = new JsonResult();
        result.setCode(500);
        result.setMessage("业务异常");
        result.setData(ex.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult exception(Exception ex) {
        JsonResult result = new JsonResult();
        result.setCode(500);
        result.setMessage("未抓获的异常");
        result.setData(ex.getMessage());
        return result;
    }
}
