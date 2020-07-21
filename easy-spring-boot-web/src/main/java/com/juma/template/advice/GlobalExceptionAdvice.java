package com.juma.template.advice;

import com.juma.template.common.JsonResult;
import com.juma.template.common.ValidFieldError;
import com.juma.template.exception.BizServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * form表单形式的参数验证
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    public JsonResult validParamterWrap(BindException ex) {
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
    public JsonResult validParamterWrap2(MethodArgumentNotValidException ex) {
        List<FieldError> errors= ex.getBindingResult().getFieldErrors();
        JsonResult result = buildValidFieldError(errors);
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
        result.setMessage("参数验证失败");
        result.setData(results);
        return result;
    }

    @ExceptionHandler(BizServiceException.class)
    @ResponseBody
    public JsonResult businessException(BizServiceException ex) {
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
