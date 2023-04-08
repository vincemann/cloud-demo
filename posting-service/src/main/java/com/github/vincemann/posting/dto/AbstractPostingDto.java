package com.github.vincemann.posting.dto;

import com.github.vincemann.springrapid.core.dto.IdAwareDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractPostingDto extends IdAwareDto<Long> {
    private String title;
    private String text;
    private Long longitude;
    private Long latitude;
    private Boolean contactable;
    private byte[] picture;

    public AbstractPostingDto(String title, String text, Long longitude, Long latitude, Boolean contactable, byte[] picture) {
        this.title = title;
        this.text = text;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contactable = contactable;
        this.picture = picture;
    }
}
