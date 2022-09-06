
package com.github.vincemann.comment.service.jpa;

import com.github.vincemann.comment.TimeoutException;
import com.github.vincemann.springrapid.core.service.JPACrudService;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;
import com.github.vincemann.springrapid.core.slicing.ServiceComponent;
import com.github.vincemann.comment.model.Comment;
import com.github.vincemann.comment.repo.CommentRepository;
import com.github.vincemann.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;


@Service
@ServiceComponent
@Slf4j
public class JpaCommentService extends JPACrudService<Comment,Long, CommentRepository> implements CommentService {

    @Override
    public Set<Comment> findAllOfParent(Long parentId) throws BadEntityException {
//        randomlyRunLong();
        Set<Comment> allByPostingId = getRepository().findAllByPostingId(parentId);
        return allByPostingId;
    }

    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum==3) sleep();
    }
    private void sleep() throws TimeoutException{
        try {
            System.out.println("Sleep");
            Thread.sleep(5000);
//            throw new TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
	