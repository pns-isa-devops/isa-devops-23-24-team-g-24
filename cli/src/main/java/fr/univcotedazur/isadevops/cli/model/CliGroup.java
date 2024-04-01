package fr.univcotedazur.isadevops.cli.model;

import java.util.Set;

public class CliGroup {
    private Long id;
    private String name;
    private Set<Long> memberIds;

    public CliGroup(Long id, String name, Set<Long> memberIds) {
        this.id = id;
        this.name = name;
        this.memberIds = memberIds;
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

    public Set<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<Long> memberIds) {
        this.memberIds = memberIds;
    }
}
