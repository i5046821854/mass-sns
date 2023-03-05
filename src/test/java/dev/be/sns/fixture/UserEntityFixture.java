package dev.be.sns.fixture;

import dev.be.sns.model.Entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String username, String password, Integer userID)
    {
        UserEntity result = new UserEntity();
        result.setId(userID);
        result.setUserName(username);
        result.setPassword(password);
        return result;
    }
}
