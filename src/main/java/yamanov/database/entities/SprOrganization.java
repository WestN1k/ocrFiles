package yamanov.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "spr_organization")
public class SprOrganization {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "code")
    private int code;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }
}
