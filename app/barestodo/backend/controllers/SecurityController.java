package barestodo.backend.controllers;


import com.google.common.base.Strings;
import models.Place;
import models.User;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import play.api.libs.oauth.OAuth;
import play.api.libs.openid.OpenID;
import play.data.format.Formats;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class SecurityController extends Controller {

    public static final String IDENT_KEY = "ident";

    @BodyParser.Of(BodyParser.Json.class)
    public static Result retrievePseudo() {
      ObjectNode result = play.libs.Json.newObject();
      User currentUser=User.findByEmail(request().getHeader(IDENT_KEY));

      if(currentUser==null){
          return notFound("user not found");
      }
      result.put("pseudo",currentUser.getPseudo());
      return ok(result);
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result register(String pseudo) {
      String emailAdress = request().getHeader(IDENT_KEY);
      if(Strings.isNullOrEmpty(emailAdress)){
         return badRequest("empty header");
      }
      User currentUser=User.findByEmail(emailAdress);
      if(currentUser!=null){
          return unauthorized("user not found");
      }
      User newUser= new User(pseudo, emailAdress);
      newUser.save();
      return ok();
    }
}