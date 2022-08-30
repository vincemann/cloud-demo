
package com.github.vincemann.posting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.vincemann.posting.dto.*;
import com.github.vincemann.posting.model.Comment;
import com.github.vincemann.springrapid.core.controller.CrudController;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.CrudDtoMappingContextBuilder;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.Direction;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.DtoMappingContext;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;
import com.github.vincemann.springrapid.core.slicing.WebController;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.service.PostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@WebController
public class PostingController extends CrudController<Posting, Long, PostingService> {

	@Override
	protected DtoMappingContext provideDtoMappingContext(CrudDtoMappingContextBuilder builder) {
		 return builder.
				 forResponse(ReadPostingDto.class)
				 .forEndpoint(getCreateUrl(), CreatePostingDto.class)
				 .forEndpoint(getUpdateUrl(), Direction.REQUEST, UpdatePostingDto.class)
				 .build();
	}


	@RequestMapping(value="/api/core/posting/find-with-comments",method = RequestMethod.GET)
	public ResponseEntity<ReadPostingWithCommentsDto> findPostingWithComments(HttpServletRequest request) throws JsonProcessingException, BadEntityException {
		Long postingId = Long.valueOf(request.getParameter("posting-id"));
		Posting postingWithComments = getService().findPostingWithComments(postingId);
		ReadPostingWithCommentsDto dto = new ReadPostingWithCommentsDto(postingWithComments);
		// set comments manually
		for (Comment comment : postingWithComments.getComments()) {
			dto.getComments().add(comment);
		}

//		getJsonMapper().writeDto(dto);
		return ResponseEntity.ok(dto);
	}



}
	