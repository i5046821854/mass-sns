package dev.be.sns.repository;

import dev.be.sns.model.Entity.PostEntity;
import dev.be.sns.model.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {

    Page<PostEntity> findAllByUser(UserEntity user, Pageable pageable);
}
