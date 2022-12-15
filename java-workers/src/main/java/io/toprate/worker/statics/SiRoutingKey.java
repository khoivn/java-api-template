package io.toprate.worker.statics;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SiRoutingKey {
    EUROPEANA("europeana"),
    SPRINGER("springer"),
    EUROPEPMC("europepmc");
    private final String name;
}
