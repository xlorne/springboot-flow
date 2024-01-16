package com.example.flow.domain;

import com.codingapi.flow.domain.user.IFlowUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_user")
@NoArgsConstructor
public class User implements IFlowUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String role;

    public long getId() {
        return id;
    }
}
