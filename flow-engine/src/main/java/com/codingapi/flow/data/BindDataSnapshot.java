package com.codingapi.flow.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BindDataSnapshot {

    /**
     * 数据快照id
     */
    private long id;
    /**
     * 快照信息
     */
    private String snapshot;
    /**
     * 创建时间
     */
    private long createTime;



}
