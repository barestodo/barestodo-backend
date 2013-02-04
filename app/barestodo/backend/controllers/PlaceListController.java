package barestodo.backend.controllers;

import models.Place;
import models.PlaceList;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 02/02/13
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */
public class PlaceListController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result retrievePlaceLists() {
        ObjectNode result = play.libs.Json.newObject();
        List<PlaceList> placeLists = PlaceList.all();
        ArrayNode actionsNode = result.putArray("Circles");
        for(PlaceList list:placeLists){
            actionsNode.add(list.toJson());
        }
        return ok(result);
    }
}
