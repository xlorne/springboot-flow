package com.example.flow.domain;

import com.codingapi.flow.domain.IBind;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_leave")
@NoArgsConstructor
public class Leave implements IBind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private Long userId;
    private Float days;
    private Long createTime;


    public long getId() {
        return id;
    }
}
