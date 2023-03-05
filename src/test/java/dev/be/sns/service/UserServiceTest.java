package dev.be.sns.service;

import dev.be.sns.exception.ErrorCode;
import dev.be.sns.exception.SnsApplicationException;
import dev.be.sns.fixture.UserEntityFixture;
import dev.be.sns.model.Entity.UserEntity;
import dev.be.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @MockBean
    UserEntityRepository userEntityRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void 회원가입_정상동작()
    {
        String userName = "userName";
        String password = "password";
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encrypt-pwd");
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(userName, password, 1));

        Assertions.assertDoesNotThrow(()->userService.join(userName, password));  //에러나 exception을 던지지 않음
    }

    @Test
    void 회원가입_중복_사용자_에러()
    {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(passwordEncoder.encode(password)).thenReturn("encrypt-pwd");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()->userService.join(userName, password));
        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());
    }

    @Test
    void 로그인_정상동작()
    {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(passwordEncoder.matches(password, fixture.getPassword())).thenReturn(true);
        Assertions.assertDoesNotThrow(()->userService.login(userName, password));  //에러나 exception을 던지지 않음
    }

    @Test
    void 로그인시_해당_username_없을경우_에러()
    {
        String userName = "userName";
        String password = "password";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(userName, password));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 로그인시_해당_password_틀릴경우_에러()
    {
        String userName = "userName";
        String password = "password";
        String wrongPwd = "wrong";

        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(userName, password));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }
}