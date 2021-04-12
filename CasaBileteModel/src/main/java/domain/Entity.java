package domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;
    public ID getID() { return id; }
    public void setID(ID id) { this.id = id; }
}
