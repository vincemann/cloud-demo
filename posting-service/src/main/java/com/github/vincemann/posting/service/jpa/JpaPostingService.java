
package com.github.vincemann.posting.service.jpa;

import com.github.vincemann.posting.config.CommercialProperties;
import com.github.vincemann.springrapid.core.service.JPACrudService;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;
import com.github.vincemann.springrapid.core.slicing.ServiceComponent;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.repo.PostingRepository;
import com.github.vincemann.posting.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@ServiceComponent
public class JpaPostingService extends JPACrudService<Posting,Long, PostingRepository> implements PostingService {

    private CommercialProperties config;

    @Autowired
    public JpaPostingService(CommercialProperties config) {
        this.config = config;
    }

    @Override
    public Posting save(Posting entity) throws BadEntityException {
        entity.setCommercialText(config.getText());
        return super.save(entity);
    }
}
	