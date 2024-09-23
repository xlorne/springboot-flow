package com.codingapi.flow.data;

public interface IBindData {

    /**
     * 获取数据ID
     * @return 数据ID
     */
    long getId();

    /**
     * 数据快照
     * @return 数据快照
     */
    String toJsonSnapshot();
}
