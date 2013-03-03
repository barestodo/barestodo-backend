package barestodo.backend.controllers;

import barestodo.backend.exception.InvalidHeaderException;
import com.google.common.base.Strings;
import models.User;
import play.mvc.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 02/03/13
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class AbstractSecuredController extends Controller {

    public static final String IDENT_KEY = "ident";


    protected static User retrieveUser(){
        String emailAdress = retrieveToken();
        User currentUser=User.findByEmail(emailAdress);
        if(currentUser==null){
            throw new IllegalArgumentException("user not found");
        }
         return currentUser;
     }

    protected static String retrieveToken() {
        String emailAdress = request().getHeader(IDENT_KEY);
        if(Strings.isNullOrEmpty(emailAdress)){
            throw new InvalidHeaderException("token not found");
        }
        return emailAdress;
    }


}
