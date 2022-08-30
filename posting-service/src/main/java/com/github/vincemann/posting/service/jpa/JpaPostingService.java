
package com.github.vincemann.posting.service.jpa;

import com.github.vincemann.posting.config.CommercialProperties;
import com.github.vincemann.posting.model.Comment;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.repo.PostingRepository;
import com.github.vincemann.posting.service.CommentService;
import com.github.vincemann.posting.service.PostingService;
import com.github.vincemann.springrapid.core.service.JPACrudService;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;
import com.github.vincemann.springrapid.core.slicing.ServiceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@ServiceComponent
@Transactional
public class JpaPostingService extends JPACrudService<Posting,Long, PostingRepository> implements PostingService {

    private CommercialProperties config;
    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public JpaPostingService(CommercialProperties config) {
        this.config = config;
    }



    @Override
    public Posting save(Posting entity) throws BadEntityException {
        entity.setCommercialText(config.getText());
        return super.save(entity);
    }

    @Override
    public Posting findPostingWithComments(Long postingId) throws BadEntityException {
        Set<Comment> comments = commentService.findCommentsOfPosting(postingId);
        Posting posting = findById(postingId).get();
        posting.setComments(comments);
        return posting;
    }
}
	