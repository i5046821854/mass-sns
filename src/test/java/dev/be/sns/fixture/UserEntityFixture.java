package dev.be.sns.fixture;

import dev.be.sns.model.Entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String username, String password)
    {
        UserEntity result = new UserEntity();
        result.setId(1);
        result.setUserName(username);
        result.setPassword(password);
        return result;
    }
}
