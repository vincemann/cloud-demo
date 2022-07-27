package com.github.vincemann.posting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadPostingDto extends AbstractPostingDto {
    private String commercialText;

    public ReadPostingDto() {
    }

    @Builder(builderMethodName = "Builder")
    public ReadPostingDto(String title, String text, Long longitude, Long latitude, Boolean contactable, byte[] picture, String commercialText) {
        super(title, text, longitude, latitude, contactable, picture);
        this.commercialText = commercialText;
    }
}
