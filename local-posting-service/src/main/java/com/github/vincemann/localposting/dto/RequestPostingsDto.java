package com.github.vincemann.localposting.dto;


import com.github.vincemann.localposting.model.GeoPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestPostingsDto{
    private GeoPosition geoPosition;
    private Float distance;
}
