package com.codingapi.flow.form;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.flow.data.IBindData;
import com.codingapi.flow.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请假表单
 */
@Getter
@AllArgsConstructor
public class Leave implements IBindData {

    /**
     *  请假ID
     */
    private long id;

    /**
     * 请假原因
     */
    private String desc;

    /**
     * 请假用户
     */
    private User user;

    /**
     *  创建时间
     */
    private long createTime;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    @Override
    public String toJsonSnapshot() {
        return JSONObject.toJSONString(this);
    }
}
