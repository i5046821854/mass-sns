package dev.be.sns.repository;

import dev.be.sns.model.Entity.LikeEntity;
import dev.be.sns.model.Entity.PostEntity;
import dev.be.sns.model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

//    @Query(value = "SELECT COUNT(e.id) from LikeEntity e WHERE e.post = :post")
//    Integer countByPost(@Param("post") PostEntity post);

    long countByPost(PostEntity post);
}