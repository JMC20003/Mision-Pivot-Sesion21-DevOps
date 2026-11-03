package com.nttdata.cambioexpress.infrastructure.adapter.in.rest;

import com.nttdata.cambioexpress.application.exception.RecursoNoEncontradoException;
import com.nttdata.cambioexpress.domain.exception.ReglaDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail handleNotFound(RecursoNoEncontradoException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ProblemDetail handleBusinessRule(ReglaDeNegocioException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
