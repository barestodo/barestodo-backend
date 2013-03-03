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

import java.util.List;

public class CircleController extends AbstractSecuredController {



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

    private static ObjectNode getMembersJsonNodes(List<User> members) {
        ObjectNode result = play.libs.Json.newObject();
        ArrayNode actionsNode = result.putArray("members");
        for(User user: members){
            actionsNode.add(user.toJson());
        }
        return result;
    }


    private static ObjectNode getCirclesJsonNodes(List<Circle> circles) {

        ObjectNode result = play.libs.Json.newObject();
        ArrayNode actionsNode = result.putArray("Circles");
        for(Circle list: circles){
            actionsNode.add(list.toJson());
        }
        return result;
    }


}
