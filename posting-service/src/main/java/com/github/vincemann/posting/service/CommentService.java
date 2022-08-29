package com.github.vincemann.posting.service;

import com.github.vincemann.posting.model.Comment;

import java.util.Set;

public interface CommentService {

    public Set<Comment> findCommentsOfPosting(Long postingId);

}
