package models;

import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class PlaceList extends Model {

    @Id
    private long id;

    private String name;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> members;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Place> placesTodo;

    public PlaceList(String name){
        super();
        this.name=name;
    }

    /** persistance statique **/
    private static Finder<Long, PlaceList> find = new Finder<Long, PlaceList>(Long.class,
            PlaceList.class);

    public static void create(PlaceList place) {
        place.save();
    }

    public static PlaceList findById(Long id) {
        return find.byId(id);
    }

    public static int count() {
        return find.findRowCount();
    }

    public static List<PlaceList> all() {
        return find.all();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

    public ObjectNode toJson() {
        ObjectNode choice = play.libs.Json.newObject();
        choice.put("id", id);
        choice.put("name", name);
        return choice;
     }
}
