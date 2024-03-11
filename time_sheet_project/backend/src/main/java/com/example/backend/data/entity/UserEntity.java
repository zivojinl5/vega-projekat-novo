package com.example.backend.data.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.backend.enums.Role;
import com.example.backend.enums.DeletedStatus;
import com.example.backend.enums.UserStatus;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_status = 'DELETED' WHERE id=?")
@SQLRestriction("deleted_status <> 'DELETED'")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus = DeletedStatus.ACTIVE;

    @ManyToMany
    @JoinTable(name = "worker_project", joinColumns = @JoinColumn(name = "worker_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<ProjectEntity> projects;

    @OneToMany(mappedBy = "lead")
    private Set<ProjectEntity> leadingProjects = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ActivityEntity> entries = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
