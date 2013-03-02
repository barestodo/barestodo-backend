package models;

import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 02/02/13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User extends Model {

    @Id
    private long id;
    private String pseudo;
    private String email;

    public String getEmail(){
        return email;
    }

    public User(String pseudo,String email){
        this.pseudo=pseudo;
        this.email=email;
    }

    public String getPseudo(){
        return pseudo;
    }

    public ObjectNode toJson() {
        ObjectNode choice = play.libs.Json.newObject();
        choice.put("id", id);
        choice.put("pseudo", pseudo);
        choice.put("email", email);
        return choice;
    }



    private static Finder<Long, User> find = new Finder<Long, User>(Long.class,
            User.class);

    public static void create(User user) {
        user.save();
    }

    public static User findById(Long id) {
        return find.byId(id);
    }

    public static User findByEmail(String email) {
        return find.where().eq("email",email).findUnique();
    }

    public static int count() {
        return find.findRowCount();
    }

    public static List<User> all() {
        return find.all();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }
}
