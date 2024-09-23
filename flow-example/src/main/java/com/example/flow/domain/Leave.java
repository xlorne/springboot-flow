package com.example.flow.domain;

import com.codingapi.flow.bind.IBind;
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
    /**
     * 请假原因
     */
    private String reason;
    /**
     * 请假人
     */
    private Long userId;

    /**
     * 请假人姓名
     */
    private String username;

    /**
     * 请假天数
     */
    private Integer days;
    /**
     * 请假开始时间
     */
    private Long createTime;


    public long getId() {
        return id;
    }
}
