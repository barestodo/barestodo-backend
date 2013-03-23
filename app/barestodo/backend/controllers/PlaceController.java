package barestodo.backend.controllers;


import barestodo.backend.exception.InvalidHeaderException;
import models.Circle;
import models.Place;
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

public class PlaceController extends AbstractSecuredController {


  @BodyParser.Of(BodyParser.Json.class)
  public static Result retrievePlacesByCircle(Long circleId) {
      try{
          ObjectNode result = play.libs.Json.newObject();
          //TODO vérifier utilisateur
          List<Place> places = Place.ofCircle(circleId);
          ArrayNode actionsNode = result.putArray("places");
          for(Place place:places){
              actionsNode.add(place.toJson());
          }
          return ok(result);
      }catch(InvalidHeaderException e){
          return forbidden(e.getMessage());
      }catch(IllegalArgumentException e){
          return forbidden(e.getMessage());
      }
  }

  @BodyParser.Of(BodyParser.Json.class)
  public static Result create(Long circleId,String name,String location){
      try{
          //TODO vérifier utilisateur
          Circle parent=Circle.findById(circleId);
          Place newPlace=new Place(parent,name,location);
          newPlace.save();
          ObjectNode result = play.libs.Json.newObject();
          result.putAll(newPlace.toJson());
          return  ok(result);
      }catch(InvalidHeaderException e){
          return forbidden(e.getMessage());
      }catch(IllegalArgumentException e){
          return forbidden(e.getMessage());
      }
  }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result get(Long id){
       try{
        //TODO vérifier utilisateur
            Place place=Place.findById(id);
            ObjectNode result = play.libs.Json.newObject();
            result.putAll(place.toJson());
            return ok(result);
       }catch(NumberFormatException e){
            return badRequest("Id must be a long");
       }catch(InvalidHeaderException e){
           return forbidden(e.getMessage());
       }catch(IllegalArgumentException e){
           return forbidden(e.getMessage());
       }catch(Exception e){
           return internalServerError(e.getMessage());
       }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result delete(Long id){
        try{
            //TODO vérifier utilisateur
            Place place=Place.findById(id);
            if(place==null){
                return notFound("no place with id "+id+" found");
            }
            place.delete();
            return ok();
        }catch(NumberFormatException e){
            return badRequest("Id must be a long");
        }catch(InvalidHeaderException e){
            return forbidden(e.getMessage());
        }catch(IllegalArgumentException e){
            return forbidden(e.getMessage());
        }catch(Exception e){
            return internalServerError(e.getMessage());
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result setEventTime(Long id, String eventTime){
        try{
            //TODO vérifier utilisateur
            Place place=Place.findById(id);
            if(place==null){
                return notFound("no place with id "+id+" found");
            }
            place.setEventTime(DateTime.parse(eventTime));
            place.save();
            return ok();
        }catch(NumberFormatException e){
            return badRequest("Id must be a long");
        }catch(InvalidHeaderException e){
            return forbidden(e.getMessage());
        }catch(IllegalArgumentException e){
            return badRequest("expected UTC format (ex:2013-03-28T19:00:00Z)");
        }catch(Exception e){
            return internalServerError(e.getMessage());
        }
    }
}