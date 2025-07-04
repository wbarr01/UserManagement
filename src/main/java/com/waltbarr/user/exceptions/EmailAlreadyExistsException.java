package com.waltbarr.user.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(){
        super("El correo ya esta registrado");
    }
}
