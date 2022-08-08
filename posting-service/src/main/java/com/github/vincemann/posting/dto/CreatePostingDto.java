package com.github.vincemann.posting.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CreatePostingDto extends AbstractPostingDto{

    @Builder
    public CreatePostingDto(String title, String text, Long longitude, Long latitude, Boolean contactable, byte[] picture) {
        super(title, text, longitude, latitude, contactable, picture);
    }
}
