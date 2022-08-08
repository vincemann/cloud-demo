
package com.github.vincemann.comment.service.jpa;

import com.github.vincemann.springrapid.core.service.JPACrudService;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;
import com.github.vincemann.springrapid.core.slicing.ServiceComponent;
import com.github.vincemann.comment.model.Comment;
import com.github.vincemann.comment.repo.CommentRepository;
import com.github.vincemann.comment.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@ServiceComponent
public class JpaCommentService extends JPACrudService<Comment,Long, CommentRepository> implements CommentService {

    @Override
    public Set<Comment> findAllOfParent(Long parentId) throws BadEntityException {
        return getRepository().findAllByPostingId(parentId);
    }
}
	