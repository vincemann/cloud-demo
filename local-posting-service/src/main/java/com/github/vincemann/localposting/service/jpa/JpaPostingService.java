
package com.github.vincemann.localposting.service.jpa;

import com.github.vincemann.localposting.model.GeoPosition;
import com.github.vincemann.localposting.util.GeoUtil;
import com.github.vincemann.springrapid.core.service.JPACrudService;
import com.github.vincemann.springrapid.core.slicing.ServiceComponent;
import com.github.vincemann.localposting.model.Posting;
import com.github.vincemann.localposting.repo.PostingRepository;
import com.github.vincemann.localposting.service.PostingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@ServiceComponent
public class JpaPostingService extends JPACrudService<Posting,Long, PostingRepository> implements PostingService {

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
	