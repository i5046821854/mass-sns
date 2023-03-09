package dev.be.sns.repository;

import dev.be.sns.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(User user){
        userRedisTemplate.opsForValue().set(getKey(user.getUsername()) ,user);
    }

    public Optional<User> getUser(String userName){
        return Optional.ofNullable(userRedisTemplate.opsForValue().get(getKey(userName)));
    }

    private String getKey(String userName){ //prefix
        return "USER:" + userName;
    }
}
