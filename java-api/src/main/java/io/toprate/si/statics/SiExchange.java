package io.toprate.si.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SiExchange {
    PUBLICATION_SYNC("PUBLICATION_SYNC");

    private final String name;
}
