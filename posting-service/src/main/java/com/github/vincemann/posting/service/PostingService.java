
package com.github.vincemann.posting.service;

import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.springrapid.core.service.CrudService;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;


public interface PostingService extends CrudService<Posting,Long> {

    public Posting findPostingWithComments(Long postingId) throws BadEntityException;

}
	