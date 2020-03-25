package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.JsonSerializable;

@JsonSerializable
@SuppressWarnings("unused")
abstract class AbstractUserTest {

    private boolean isCreated;

    public AbstractUserTest() {
        isCreated = true;
    }

    public boolean isCreated() {
        return isCreated;
    }
}
