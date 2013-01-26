package barestodo.backend.controllers;


import models.Place;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

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
      return  ok();
  }
}