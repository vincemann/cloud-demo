package com.github.vincemann.posting.model;

import com.github.vincemann.springrapid.core.model.AuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends AuditingEntity<Long> {

    private Long postingId;
    private String text;

}
