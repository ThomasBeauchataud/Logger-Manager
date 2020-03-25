package com.github.ffcfalcos.logger.interceptor;

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
