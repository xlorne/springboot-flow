package com.example.flow.domain;

import com.codingapi.flow.user.IFlowUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@Table(name = "t_user")
@NoArgsConstructor
@ToString
public class User implements IFlowUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    public long getId() {
        return id;
    }
}
