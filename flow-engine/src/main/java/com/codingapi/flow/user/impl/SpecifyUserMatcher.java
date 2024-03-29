package com.codingapi.flow.user.impl;

import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.user.IFlowUserMatcher;

import java.util.List;

/**
 * 指定用户匹配器
 */
public class SpecifyUserMatcher implements IFlowUserMatcher {

    private final List<? extends IFlowUser> users;

    public SpecifyUserMatcher(List<? extends IFlowUser> users) {
        this.users = users;
    }

    @Override
    public boolean match(IFlowUser user) {
        List<Long> userIds = users.stream().map(IFlowUser::getId).toList();
        return userIds.contains(user.getId());
    }
}
