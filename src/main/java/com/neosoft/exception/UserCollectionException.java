package com.neosoft.exception;

public class UserCollectionException extends Exception{

    public UserCollectionException(String message) {

        super(message);

    }

    public static String NotFoundException(String id){

        return "User with" +id+ "not found!";
    }

    public static String UserAlreadyExist(){

        return "User with the given name already exist!";
    }
}
