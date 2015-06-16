package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import play.db.ebean.Model;

@MappedSuperclass
public abstract class BaseEntity extends Model{

    @Id
    public Long id;
    @Version
    @Column(columnDefinition = "integer default 1")
    public int version;

    /**
     * this method is used internally for json entity validation
     * @return the corresponding ObjectNode
     */
    public abstract ObjectNode toJson();    
    
}
