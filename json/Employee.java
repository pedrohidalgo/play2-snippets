package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.persistence.Entity;
import play.libs.Json;

@Entity
public class Employee extends BaseEntity {
    
    public String firstName;
    public String lastName;

    @Override
    public ObjectNode toJson() {
        ObjectNode objectNode = Json.newObject();

        objectNode.put("id", id);
        objectNode.put("firstName", firstName);
        objectNode.put("lastName", lastName);

        return objectNode;
    }

}
