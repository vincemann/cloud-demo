
package com.github.vincemann.comment.controller;

import com.github.vincemann.comment.dto.CommentDto;
import com.github.vincemann.springrapid.core.controller.CrudController;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.CrudDtoMappingContextBuilder;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.DtoMappingContext;
import com.github.vincemann.springrapid.core.controller.parentAware.ParentAwareCrudController;
import com.github.vincemann.springrapid.core.controller.parentAware.ParentAwareDtoMappingContextBuilder;
import com.github.vincemann.springrapid.core.slicing.WebController;
import com.github.vincemann.comment.model.Comment;
import com.github.vincemann.comment.service.CommentService;

@WebController
public class CommentController extends ParentAwareCrudController<Comment, Long,Long, CommentService> {

	@Override
	protected DtoMappingContext provideDtoMappingContext(ParentAwareDtoMappingContextBuilder builder) {
		return builder.forAll(CommentDto.class)
				.build();
	}


}
	