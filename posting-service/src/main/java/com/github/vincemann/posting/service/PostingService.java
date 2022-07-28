
package com.github.vincemann.posting.service;
import com.github.vincemann.posting.model.GeoPosition;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.springrapid.core.service.CrudService;

import java.util.Set;


public interface PostingService extends CrudService<Posting,Long> {
    Float MAX_DISTANCE = 1000f;

    Set<Posting> findNearbyPostings(GeoPosition position, Float distance);
	
}
	