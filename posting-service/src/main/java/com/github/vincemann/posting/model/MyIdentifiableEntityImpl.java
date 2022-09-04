package com.github.vincemann.posting.model;

import com.github.vincemann.smartlogger.SmartLogger;
import com.github.vincemann.springrapid.core.model.IdentifiableEntity;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
public class MyIdentifiableEntityImpl implements IdentifiableEntity<Long> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    private Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.github.vincemann.springrapid.core.model.IdentifiableEntityImpl<?> other = (com.github.vincemann.springrapid.core.model.IdentifiableEntityImpl<?>) o;
        // added null check here, otherwise entities with null ids are considered equal
        // and cut down to one entity in a set for example
        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return SmartLogger.builder()
                .build()
                .toString(this);
    }
}
