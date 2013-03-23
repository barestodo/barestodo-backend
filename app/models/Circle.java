package models;

import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import java.util.List;


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

    public ObjectNode toJson() {
        ObjectNode choice = play.libs.Json.newObject();
        choice.put("id", id);
        choice.put("name", name);
        return choice;
    }

    public ObjectNode toJsonRef() {
        ObjectNode choice = play.libs.Json.newObject();
        choice.put("id", id);
        choice.put("name", name);
        choice.put("nbMembers",members.size());
        choice.put("nbPlaces",countPlaces(id));
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

    public static int countMembers(long circleId){
        throw new NotImplementedException();//return find.setQuery("select user_id from circle_user").where().eq("circle_id",circleId).findRowCount();
    }

    public static int countPlaces(long circleId){
        return Place.ofCircle(circleId).size();
    }
    public static List<Circle> all(Long userId) {
        return find.where().eq("members.id", userId).findList();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

}
