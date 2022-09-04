package com.github.vincemann.posting.dto;


import com.github.vincemann.posting.model.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CreatePostingDto extends AbstractPostingDto{

    @Builder
    public CreatePostingDto(String title, String text, Long longitude, Long latitude, Boolean contactable/*, byte[] picture*/) {
        super(title, text, longitude, latitude, contactable/*, picture*/);
    }

    public CreatePostingDto(Posting posting) {
        super(posting.getTitle(), posting.getText(), posting.getLongitude(), posting.getLatitude(), posting.getContactable()/*, posting.getPicture()*/);
    }
}
