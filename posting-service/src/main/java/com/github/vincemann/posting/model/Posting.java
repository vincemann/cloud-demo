
package com.github.vincemann.posting.model;

import com.github.vincemann.springrapid.core.model.AuditingEntity;
import com.github.vincemann.springrapid.core.model.IdentifiableEntityImpl;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "posting")
public class Posting extends AuditingEntity<Long> {

    private String commercialText;
    private String title;
    private String text;
    private Long longitude;
    private Long latitude;
    private Boolean contactable;
    private byte[] picture;

    // get from comment service
    @Transient
    private Set<Comment> comments = new HashSet<>();

    @Builder
    public Posting(String commercialText, String title, String text, Long longitude, Long latitude, Boolean contactable, byte[] picture, Set<Comment> comments) {
        this.commercialText = commercialText;
        this.title = title;
        this.text = text;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contactable = contactable;
        this.picture = picture;
        this.comments = comments;
    }
}
	