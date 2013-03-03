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
public class Circle extends Model {

    @Id
    private long id;

    private String name;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> members;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Place> placesTodo;

    public Circle(String name){
        super();
        this.name=name;
    }

    /** persistance statique **/
    private static Finder<Long, Circle> find = new Finder<Long, Circle>(Long.class,
            Circle.class);

    public static void create(Circle place) {
        place.save();
    }

    public static Circle findById(Long id) {
        return find.byId(id);
    }

    public static int count() {
        return find.findRowCount();
    }

    public static List<Circle> all() {
        return find.all();
    }

    public static List<Circle> all(Long userId) {
        return find.where().eq("members.id",userId).findList();
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

    public List<User> getMembers() {
        return members;
    }

    public long getId() {
        return id;
    }

    public void addMember(User member) {
        members.add(member);
    }

    public void removeMember(User member) {
        members.remove(member);
    }
}
