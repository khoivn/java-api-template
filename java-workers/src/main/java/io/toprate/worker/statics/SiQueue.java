package io.toprate.worker.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SiQueue {
    EUROPEANA("europeana_sync"),
    SPRINGER("springer_sync"),
    EUROPEPMC("europepmc_sync");

    private final String name;
}
