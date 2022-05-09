package ru.aora.erp.model.entity.db.user;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class DbRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "uniqueidentifier", unique = true)
    private String id;

    @Column(name = "name", unique = true)
    private String name;

//    @ManyToMany(mappedBy = "subAuthorities", fetch=FetchType.LAZY)
//    private Collection<DbAuthority> authorities;

    public String getId() {
        return id;
    }

    public DbRole setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DbRole setName(String name) {
        this.name = name;
        return this;
    }


//    public Collection<DbAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(Collection<DbAuthority> authorities) {
//        this.authorities = authorities;
//    }

    @Override
    public String toString() {
        return "DbSubAuthority{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
//                ", authorities=" + authorities +
                '}';
    }
}
