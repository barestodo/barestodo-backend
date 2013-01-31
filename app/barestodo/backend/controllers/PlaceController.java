package barestodo.backend.controllers;


import models.Place;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class PlaceController extends Controller {

  @BodyParser.Of(BodyParser.Json.class)
  public static Result retrievePlaces() {
      ObjectNode result = play.libs.Json.newObject();
      List<Place> places = Place.all();
      ArrayNode actionsNode = result.putArray("places");
      for(Place place:places){
          actionsNode.add(place.toJson());
      }
      return ok(result);
  }

  @BodyParser.Of(BodyParser.Json.class)
  public static Result create(String name,String location){
      Place newPlace=new Place(name,location);
      newPlace.save();
      ObjectNode result = play.libs.Json.newObject();
      result.putAll(newPlace.toJson());
      return  ok(result);
  }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result get(Long id){
       try{
        Place place=Place.findById(id);
        ObjectNode result = play.libs.Json.newObject();
        result.putAll(place.toJson());
        return ok(result);
       }catch(NumberFormatException e){
            return badRequest("Id must be a long");
       }catch(Exception e){
           return internalServerError(e.getMessage());
       }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result delete(Long id){
        try{
            Place place=Place.findById(id);
            if(place==null){
                return notFound("no place with id "+id+" found");
            }
            place.delete();
            return ok();
        }catch(NumberFormatException e){
            return badRequest("Id must be a long");
        }catch(Exception e){
            return internalServerError(e.getMessage());
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result setEventTime(Long id, String eventTime){
        try{
            Place place=Place.findById(id);
            if(place==null){
                return notFound("no place with id "+id+" found");
            }
            place.setEventTime(DateTime.parse(eventTime));
            place.save();
            return ok();
        }catch(NumberFormatException e){
            return badRequest("Id must be a long");
        }catch(Exception e){
            return internalServerError(e.getMessage());
        }
    }
}