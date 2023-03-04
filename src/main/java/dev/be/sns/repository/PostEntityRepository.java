package dev.be.sns.repository;

import dev.be.sns.model.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {
}
