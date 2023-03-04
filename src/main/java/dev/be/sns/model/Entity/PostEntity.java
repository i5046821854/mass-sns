package dev.be.sns.model.Entity;

import dev.be.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() whrere id = ?")  //delete 될때 실행할 것
@Where(clause = "deleted_at is NULL")  //쿼리 수행시 조건
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt()
    {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt()
    {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static PostEntity of(String title, String body, UserEntity userEntity)
    {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setBody(body);
        postEntity.setUser(userEntity);
        return postEntity;
    }
}
