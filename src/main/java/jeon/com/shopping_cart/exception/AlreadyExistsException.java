package jeon.com.shopping_cart.exception;

import org.springframework.core.StandardReflectionParameterNameDiscoverer;

public class AlreadyExistsException extends  RuntimeException{
    public AlreadyExistsException(String message){
        super(message);
    }
}
