package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.JsonSerializable;
import com.github.ffcfalcos.logger.LogIgnored;

@JsonSerializable
@SuppressWarnings("unused")
class AccountTest {

    @LogIgnored
    private String password;
    private String username;

    AccountTest() {
        password = "my_password";
        username = "my_username";
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
