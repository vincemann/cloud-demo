package com.github.vincemann.comment.dto;

import com.github.vincemann.springrapid.core.dto.IdAwareDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto extends IdAwareDto<Long> {
    private String text;
    private Long postingId;

    @Builder
    public CommentDto(String text, Long postingId) {
        this.text = text;
        this.postingId = postingId;
    }
}
