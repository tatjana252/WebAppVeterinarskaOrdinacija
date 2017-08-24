/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author hp
 */
public class RESTException extends Throwable {

    String message;

    public RESTException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return (message);
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    
}
