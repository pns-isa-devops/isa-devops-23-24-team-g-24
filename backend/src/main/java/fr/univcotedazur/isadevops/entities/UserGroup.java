package fr.univcotedazur.isadevops.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<Customer> members = new HashSet<>();

    public UserGroup() {
    }
    public UserGroup(String name, Set<Customer> members) {
        this.name = name;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Customer> getMembers() {
        return members;
    }

    public void setMembers(Set<Customer> members) {
        this.members = members;
    }
}

