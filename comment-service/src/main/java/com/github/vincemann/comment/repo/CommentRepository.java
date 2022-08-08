
package com.github.vincemann.comment.repo;
import com.github.vincemann.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Set<Comment> findAllByPostingId(Long postingId);
}
	