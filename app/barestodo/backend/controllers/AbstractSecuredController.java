package barestodo.backend.controllers;

import barestodo.backend.exception.InvalidHeaderException;
import com.google.common.base.Strings;
import models.User;
import play.mvc.Controller;


public class AbstractSecuredController extends Controller {

    public static final String IDENT_KEY = "ident";


    protected static User retrieveUser(){
        String emailAdress = retrieveToken();
        return retrieveUser(emailAdress);
     }

    protected static String retrieveToken() {
        String emailAdress = request().getHeader(IDENT_KEY);
        if(Strings.isNullOrEmpty(emailAdress)){
            throw new InvalidHeaderException("token not found");
        }
        return emailAdress;
    }

    protected static User retrieveUser(String email) {
        User user=User.findByEmail(email);
        if(user==null){
            throw new IllegalArgumentException("user not found");
        }
        return user;
    }
}
