package com.User.exception;

public class UserNotFoundException  extends RuntimeException{


    // Default constructor
    public UserNotFoundException() {
        super("User not found");
    }

    // Constructor with custom message
    public UserNotFoundException(String message) {
        super(message);
    }


	
}
