package models;

import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

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


    public ObjectNode toJson() {
        ObjectNode choice = play.libs.Json.newObject();
        choice.put("id", id);
        choice.put("pseudo", pseudo);
        choice.put("email", email);
        return choice;
    }
}
