package com.github.vincemann.posting.dto;

import com.github.vincemann.springrapid.core.model.AbstractDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SnippetPostingDto extends AbstractDto<Long> {

    private Double longitude;
    private Double latitude;
    private String textSnippet;
    private String title;
}
