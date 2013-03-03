package models;

import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Place extends Model {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="place_list_id", nullable=false)
    private Circle parent;

    private String name;
    private String location;
    private DateTime eventTime;

    public Place(Circle parent, String name, String location){
        super();
        this.parent=parent;
        this.name=name;
        this.location=location;
    }

    public void setEventTime(DateTime eventTime){
         this.eventTime=eventTime;
    }



    public ObjectNode toJson() {
        ObjectNode choice = play.libs.Json.newObject();
        choice.put("id", id);
        choice.put("name", name);
        choice.put("location", location);
        if(eventTime!=null){
            choice.put("eventTime", eventTime.toString("yyyy-MM-dd"));
        }
        return choice;
    }


    /** persistance statique **/
    private static Finder<Long, Place> find = new Finder<Long, Place>(Long.class,
            Place.class);

    public static void create(Place place) {
        place.save();
    }

    public static Place findById(Long id) {
        return find.byId(id);
    }

    public static int count() {
        return find.findRowCount();
    }

    public static List<Place> all() {
        return find.all();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

    public static List<Place> ofCircle(Long circleId) {
        return find.where().eq("parent.id",circleId).findList();
    }
}
