package dev.be.sns.service;

import dev.be.sns.exception.ErrorCode;
import dev.be.sns.exception.SnsApplicationException;
import dev.be.sns.fixture.PostEntityFixture;
import dev.be.sns.fixture.UserEntityFixture;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(e.getErrorCode(), ErrorCode.USER_NOT_FOUND);
    }

    @Test
    void 포스트수정성공(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(()->postService.modify(title, body, userName, postId));

    }

    @Test
    void 포스트수정시_포스트가_존재하지_않는_경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, userName, postId));
        assertEquals(ErrorCode.POST_NOT_FOUND,e.getErrorCode());

    }

    @Test
    void 포스트수정시_권한이_없는_경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = UserEntityFixture.get("user", "pwd", 2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, userName, postId));
        assertEquals(ErrorCode.INVALID_PERMISSION,e.getErrorCode());

    }

    @Test
    void 포스트삭제_성공(){
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(()->postService.delete(userName, postId));

    }

    @Test
    void 포스트삭제시_포스트가_존재하지_않는_경우(){
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete( userName, postId));
        assertEquals(ErrorCode.POST_NOT_FOUND,e.getErrorCode());
    }

    @Test
    void 포스트삭제시_권한_없는_경우(){
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = UserEntityFixture.get("username", "password", 2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete( userName, 1));
        assertEquals(ErrorCode.INVALID_PERMISSION,e.getErrorCode());
    }

    @Test
    void 피드목록요청이_성공한경우(){
        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAll(pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(()->postService.list(pageable));
    }

    @Test
    void 내피드목록요청이_성공한경우(){
        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAllByUser(any(), pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(()->postService.my("", pageable));
    }
}
