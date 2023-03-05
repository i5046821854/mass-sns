package dev.be.sns.fixture;

import dev.be.sns.model.Entity.PostEntity;
import dev.be.sns.model.Entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String username, Integer postId)
    {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUserName(username);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}
