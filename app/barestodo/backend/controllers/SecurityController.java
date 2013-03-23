package barestodo.backend.controllers;


import barestodo.backend.exception.InvalidHeaderException;
import com.google.common.base.Strings;
import models.User;
import org.codehaus.jackson.node.ObjectNode;
import play.mvc.BodyParser;
import play.mvc.Result;

public class SecurityController extends AbstractSecuredController {


    @BodyParser.Of(BodyParser.Text.class)
    public static Result retrievePseudo() {
        try {
            User currentUser = retrieveUser();
            return ok(currentUser.getPseudo());
        } catch (IllegalArgumentException e) {
            return notFound("unknow user");
        } catch (InvalidHeaderException e) {
            return badRequest("unknow user");
        }
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result register(String pseudo) {
        try {
            String emailAdress = retrieveToken();
            User currentUser = User.findByEmail(emailAdress);
            if (currentUser != null) {
                return forbidden("user already exist");
            }
            User newUser = new User(pseudo, emailAdress);
            newUser.save();
            return ok();
        } catch (InvalidHeaderException e) {
            return badRequest(e.getMessage());
        }
    }


}