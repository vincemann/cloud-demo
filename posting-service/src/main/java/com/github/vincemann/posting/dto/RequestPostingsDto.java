package com.github.vincemann.posting.dto;

import com.github.vincemann.posting.model.GeoPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestPostingsDto{
    private GeoPosition geoPosition;
    private Float distance;
}
