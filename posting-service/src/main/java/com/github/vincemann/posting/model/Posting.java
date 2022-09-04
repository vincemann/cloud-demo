
package com.github.vincemann.posting.model;

import com.github.vincemann.springrapid.core.model.AuditingEntity;
import com.github.vincemann.springrapid.core.model.IdentifiableEntityImpl;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
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


    @Column(name = "commercial_text")
    private String commercialText;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "longitude")
    private Long longitude;
    @Column(name = "latitude")
    private Long latitude;
    @Column(name = "contactable")
    private Boolean contactable;
//    @Column(name = "picture")
//    private byte[] picture;

    // get from comment service
//    @Transient
//    private Set<Comment> comments = new HashSet<>();

    @Builder
    public Posting(String commercialText, String title, String text, Long longitude, Long latitude, Boolean contactable/*, byte[] picture*//*, Set<Comment> comments*/) {
        this.commercialText = commercialText;/*, Set<Comment> comments*/
        this.title = title;
        this.text = text;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contactable = contactable;
//        this.picture = picture;
//        if (comments != null)
//            this.comments = comments;
    }
}
	