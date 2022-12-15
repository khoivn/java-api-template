package io.toprate.si.hashKey;

import io.toprate.si.statics.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HashKey implements Serializable {
    private Integer pageNumber;
    private Integer pageSize;
    private Resource resource;
}
