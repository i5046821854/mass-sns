package dev.be.sns.service;

import dev.be.sns.exception.ErrorCode;
import dev.be.sns.exception.SnsApplicationException;
import dev.be.sns.model.Alarm;
import dev.be.sns.model.Entity.UserEntity;
import dev.be.sns.model.User;
import dev.be.sns.repository.AlarmEntityRepository;
import dev.be.sns.repository.UserEntityRepository;
import dev.be.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private long expiredTimeMs;



    public User loadUserByUserName(String userName) {
        return userEntityRepository.findByUserName(userName)
                .map(User::fromEntity)
                .orElseThrow(()->
                        new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not found", userName)));
    }

    @Transactional
    public User join(String userName, String password)
    {
        System.out.println("hello2");
        userEntityRepository.findByUserName(userName).ifPresent(it->{
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        //회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded)", userName)));
        //비밀번호 체크
        if(encoder.matches(password, userEntity.getPassword())){
        //if(!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        //토큰 생성
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);
        return "";
    }

    @Transactional
    public Page<Alarm> alarmList(String userName, Pageable pageable) {
        UserEntity user = userEntityRepository.findByUserName(userName).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
        );
        return alarmEntityRepository.findAllByUser(user, pageable).map(Alarm::fromEntity);
    }
}
