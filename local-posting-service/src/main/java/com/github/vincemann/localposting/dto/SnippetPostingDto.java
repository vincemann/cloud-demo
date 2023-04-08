package com.github.vincemann.localposting.dto;

import com.github.vincemann.springrapid.core.dto.IdAwareDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SnippetPostingDto extends IdAwareDto<Long> {

    private Double longitude;
    private Double latitude;
    private String textSnippet;
    private String title;
}
