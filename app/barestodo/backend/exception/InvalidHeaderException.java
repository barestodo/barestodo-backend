package barestodo.backend.exception;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 02/03/13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public class InvalidHeaderException extends RuntimeException{

    public InvalidHeaderException(String message){
         super(message);
    }

    public InvalidHeaderException(String message,Throwable e){
         super(message,e);
    }


}
