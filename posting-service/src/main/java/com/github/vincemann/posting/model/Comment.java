package com.github.vincemann.posting.model;

import com.github.vincemann.springrapid.core.model.AuditingEntity;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends AuditingEntity<Long> {

    private Long postingId;
    private String text;

}
