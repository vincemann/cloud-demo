
package com.github.vincemann.posting.service.jpa;

import com.github.vincemann.posting.config.CommercialProperties;
import com.github.vincemann.posting.model.GeoPosition;
import com.github.vincemann.posting.util.GeoUtil;
import com.github.vincemann.springrapid.core.service.JPACrudService;
import com.github.vincemann.springrapid.core.service.exception.BadEntityException;
import com.github.vincemann.springrapid.core.slicing.ServiceComponent;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.repo.PostingRepository;
import com.github.vincemann.posting.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@ServiceComponent
@Transactional
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

    @Override
    public Set<Posting> findNearbyPostings(GeoPosition position, Float distance) {
        List<Posting> postings = getRepository().findAll();
        float realDistance = (distance > MAX_DISTANCE) ? MAX_DISTANCE : distance;
        Set<Posting> inRange = postings.parallelStream()
                .filter(p -> {
                    double d = GeoUtil.getGeoLocationDistance(p.getLatitude(), p.getLongitude(), position.getLatitude(), position.getLongitude(), 'K');
                    return d <= realDistance;
                })
                .collect(Collectors.toSet());
        return inRange;
    }
}
	