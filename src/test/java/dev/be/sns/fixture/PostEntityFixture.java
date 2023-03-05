package dev.be.sns.fixture;

import dev.be.sns.model.Entity.PostEntity;
import dev.be.sns.model.Entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String username, Integer postId, Integer userId)
    {
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUserName(username);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}
