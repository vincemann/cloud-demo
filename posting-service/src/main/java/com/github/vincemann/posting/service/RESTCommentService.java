package com.github.vincemann.posting.service;

import com.github.vincemann.posting.model.Comment;
import com.github.vincemann.springrapid.core.util.ArrayUtils;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RESTCommentService implements CommentService {

    private RestTemplate restTemplate;

    @Autowired
    public RESTCommentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "commentService", fallbackMethod = "buildFallbackCommentSet")
    @RateLimiter(name = "commentService", fallbackMethod = "buildFallbackCommentSet")
    @Retry(name = "retryCommentService", fallbackMethod = "buildFallbackCommentSet")
    @Bulkhead(name = "bulkheadCommentService", type= Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackCommentSet")
    @Override
    public Set<Comment> findCommentsOfPosting(Long postingId) {
        ResponseEntity<Comment[]> restExchange =
                restTemplate.exchange(
                        "http://comment-service/api/core/comment/find-all-of-parent",
                        HttpMethod.GET,
                        null, Comment[].class, postingId);
        @SuppressWarnings("all")
        Set<Comment> comments = ArrayUtils.arrayToSet(restExchange.getBody());
        return comments;
    }

    @SuppressWarnings("unused")
    public Set<Comment> buildFallbackCommentSet(Long postingId,Throwable t){
        log.error("error while locating comments:",t);
        Set<Comment> defaultComments = new HashSet<>();
        defaultComments.add(Comment.builder()
                        .postingId(postingId)
                        .text("Could not locate comments")
                .build());
        return defaultComments;
    }
}
