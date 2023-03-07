package dev.be.sns.model.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"like\"", indexes = {
        @Index(name = "post_id_idx", columnList = "post_id")
})
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() whrere id = ?")  //delete 될때 실행할 것
@Where(clause = "deleted_at is NULL")  //쿼리 수행시 조건
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

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

    public static LikeEntity of(UserEntity userEntity, PostEntity postEntity)
    {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setUser(userEntity);
        likeEntity.setPost(postEntity);
        likeEntity.setUser(userEntity);
        return likeEntity;
    }
}
