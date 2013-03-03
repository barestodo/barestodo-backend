package barestodo.backend.controllers;

import models.Place;
import org.codehaus.jackson.JsonNode;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.Json;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class PlaceControllerIntegrationTest {


    @BeforeClass
    public static void init(){
        new Place(parent, "Troll","rue de cote").save();
        new Place(parent, "24","Saint lazare");
        new Place(parent, "l'arcade","Saint lazare");
        new Place(parent, "toutalégou","rue de la pente rude");
    }

    @Test
    public void place_controller_return_json_in_utf8(){
        play.mvc.Result actions = callAction(barestodo.backend.controllers.routes.ref.PlaceController.retrievePlaces());
        assertThat(status(actions)).isEqualTo(OK);

        assertThat(contentType(actions)).isEqualTo("application/json");
        assertThat(charset(actions)).isEqualTo("utf-8");
     }

    @Test
    public void place_controller_return_some_objects(){
        play.mvc.Result actions = callAction(barestodo.backend.controllers.routes.ref.PlaceController.retrievePlaces());
        assertThat(status(actions)).isEqualTo(OK);
        String actionsString=contentAsString(actions);
        JsonNode json=Json.parse(actionsString);

        assertThat(json.findPath("places")).isNotNull();
        assertThat(json.findPath("places")).hasSize(4);
    }


}
