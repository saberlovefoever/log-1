package org.whh.bz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST,reason = "SESSION ERROR")
public class UserSessionException extends NullPointerException{
}
