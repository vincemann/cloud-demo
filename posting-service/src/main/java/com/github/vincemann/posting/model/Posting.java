
package com.github.vincemann.posting.model;

import com.github.vincemann.springrapid.core.model.AuditingEntity;
import com.github.vincemann.springrapid.core.model.IdentifiableEntityImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

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

}
	