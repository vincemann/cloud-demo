package com.github.vincemann.posting.dto;

import com.github.vincemann.posting.model.Comment;
import com.github.vincemann.posting.model.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ReadPostingWithCommentsDto extends AbstractPostingDto{

    private String commercialText;

    private Set<Comment> comments = new HashSet<>();

    public ReadPostingWithCommentsDto(Posting posting){
        this(posting.getTitle(),posting.getText(),posting.getLongitude(),posting.getLatitude(),posting.getContactable(),/*posting.getPicture(),*/posting.getCommercialText(),new HashSet<>());
    }

    @Builder
    public ReadPostingWithCommentsDto(String title, String text, Long longitude, Long latitude, Boolean contactable,/* byte[] picture,*/ String commercialText, Set<Comment> comments) {
        super(title, text, longitude, latitude, contactable/*, picture*/);
        this.commercialText = commercialText;
        this.comments = comments;
    }
}
