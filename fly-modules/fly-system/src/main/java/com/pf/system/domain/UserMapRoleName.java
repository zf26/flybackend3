package com.pf.system.domain;

import java.util.List;

public class UserMapRoleName {
    private Long userId;
    private List<String> roleNames;

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
