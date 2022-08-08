
package com.github.vincemann.comment.service;
import com.github.vincemann.comment.model.Comment;
import com.github.vincemann.springrapid.core.service.CrudService;
import com.github.vincemann.springrapid.core.service.ParentAwareService;


public interface CommentService extends CrudService<Comment,Long>, ParentAwareService<Comment,Long> {
	
}
	