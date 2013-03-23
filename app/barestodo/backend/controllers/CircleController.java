package barestodo.backend.controllers;

import barestodo.backend.exception.InvalidHeaderException;
import models.Circle;
import models.User;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static models.User.getMembersJsonNodes;

public class CircleController extends AbstractSecuredController {



    @BodyParser.Of(BodyParser.Json.class)
    public static Result leave(Long circleId) {
        User currentUser=retrieveUser();
        Circle newCircle=Circle.findById(circleId);
        newCircle.removeMember(currentUser);
        newCircle.save();
        ObjectNode result = play.libs.Json.newObject();
        result.putAll(newCircle.toJson());
        return ok(result);
    }

    public static Result addPerson(Long circleId,String email){
        User userToInvite;
        userToInvite = retireveUserByEmailOrCreateThem(email);
        return trytoAddMemberOnCircle(circleId, userToInvite);
    }

    private static User retireveUserByEmailOrCreateThem(String email) {
        User userToInvite;
        try{
           userToInvite =retrieveUser(email);
        }catch(IllegalArgumentException e){
            userToInvite=new User(email);
            userToInvite.save();
        }
        return userToInvite;
    }

    private static Result trytoAddMemberOnCircle(Long circleId, User userToInvite) {
        Circle circleToAddUser=Circle.findById(circleId);
        circleToAddUser.addMember(userToInvite);
        try{
            circleToAddUser.save();
            return ok();
        }catch(PersistenceException e){
          return status(CONFLICT,"user already on this circle");
        }
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result create(String name) {
        User currentUser=retrieveUser();
        Circle newCircle=new Circle(name);
        newCircle.addMember(currentUser);
        newCircle.save();
        ObjectNode result = play.libs.Json.newObject();
        result.putAll(newCircle.toJson());
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result retrieveCircles() {
      try{
        User currentUser=retrieveUser();
        List<Circle> circles = Circle.all(currentUser.getId());
        return ok(getCirclesJsonNodes(circles));
      }catch(InvalidHeaderException e){
            return forbidden(e.getMessage());
      }catch(IllegalArgumentException e){
           return forbidden(e.getMessage());
      }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result retrieveCircleMembers(Long circleId) {
        try{
            Logger.info("try to retrieve members for circle "+circleId);

            User currentUser=retrieveUser();
            List<User> members = Circle.findById(circleId).getMembers();
            if(!members.contains(currentUser)){
                return forbidden("current user not on this circle");
            }
            return ok(getMembersJsonNodes(members));
        }catch(InvalidHeaderException e){
            Logger.error(e.getMessage(),e);
            return forbidden(e.getMessage());
        }catch(IllegalArgumentException e){
            Logger.error(e.getMessage(),e);
            return forbidden(e.getMessage());
        }
    }




    private static ObjectNode getCirclesJsonNodes(List<Circle> circles) {

        ObjectNode result = play.libs.Json.newObject();
        ArrayNode actionsNode = result.putArray("Circles");
        for(Circle list: circles){
            actionsNode.add(list.toJsonRef());
        }
        return result;
    }


}
