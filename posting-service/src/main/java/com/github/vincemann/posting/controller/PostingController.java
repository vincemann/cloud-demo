
package com.github.vincemann.posting.controller;

import com.github.vincemann.posting.dto.CreatePostingDto;
import com.github.vincemann.posting.dto.ReadPostingDto;
import com.github.vincemann.posting.dto.UpdatePostingDto;
import com.github.vincemann.springrapid.core.controller.CrudController;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.CrudDtoMappingContextBuilder;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.Direction;
import com.github.vincemann.springrapid.core.controller.dto.mapper.context.DtoMappingContext;
import com.github.vincemann.springrapid.core.slicing.WebController;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.service.PostingService;

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
}
	