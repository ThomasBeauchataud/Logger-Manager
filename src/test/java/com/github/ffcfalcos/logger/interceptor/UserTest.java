package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.*;

@JsonSerializable
@SuppressWarnings("all")
class UserTest extends AbstractUserTest {

    private int index;
    private AccountTest account;

    UserTest() {
        super();
        index = 1;
        account = new AccountTest();
    }

    public int getIndex() {
        return index;
    }

    @TraceBefore(persistingHandlerClass = FilePersistingHandler.class)
    boolean isValidFirstUsername(String username) {
        return account.getUsername().equals(username);
    }

    @TraceAfter(persistingHandlerClass = FilePersistingHandler.class)
    boolean isValidSecondUsername(String username) {
        return account.getUsername().equals(username);
    }

    @TraceAround(persistingHandlerClass = FilePersistingHandler.class, formatterHandlerClass = JsonFormatterHandler.class, context = true)
    boolean isValidThirdUsername(String username) {
        return account.getUsername().equals(username);
    }

    @TraceAfterReturning(persistingHandlerClass = FilePersistingHandler.class)
    boolean isValidFourthUsername(String username) {
        return account.getUsername().equals(username);
    }

    @TraceAfterThrowing(persistingHandlerClass = FilePersistingHandler.class)
    boolean isValidFifthUsername() throws Exception {
        throw new Exception("Exception thrown");
    }

}
