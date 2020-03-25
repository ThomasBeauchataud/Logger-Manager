package com.github.ffcfalcos.logger.interceptor;

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
