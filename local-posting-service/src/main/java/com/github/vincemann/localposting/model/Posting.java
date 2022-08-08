
package com.github.vincemann.localposting.model;

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

    private Double longitude;
    private Double latitude;
    private String textSnippet;
    private String title;
}
	