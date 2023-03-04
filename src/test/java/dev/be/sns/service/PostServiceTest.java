package dev.be.sns.service;

import dev.be.sns.exception.ErrorCode;
import dev.be.sns.exception.SnsApplicationException;
import dev.be.sns.model.Entity.PostEntity;
import dev.be.sns.model.Entity.UserEntity;
import dev.be.sns.repository.PostEntityRepository;
import dev.be.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {


    @Autowired
    private PostService postService;
    @MockBean
    private PostEntityRepository postEntityRepository;
    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 포스트작성성공(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(()->postService.create(title, body, userName));
    }

    @Test
    void 포스트작성시_요청한_유저가_존재하지_않는_경우우(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()->postService.create(title, body, userName));
        Assertions.assertEquals(e.getErrorCode(), ErrorCode.USER_NOT_FOUND);
    }
}
