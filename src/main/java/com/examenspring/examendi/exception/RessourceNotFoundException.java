package com.examenspring.examendi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class RessourceNotFoundException extends ApiBaseException {

    public RessourceNotFoundException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatusCode(){
        return HttpStatus.NOT_FOUND;
    }
}
