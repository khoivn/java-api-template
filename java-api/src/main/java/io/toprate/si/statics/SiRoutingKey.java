package io.toprate.si.statics;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SiRoutingKey {
    EUROPEANA("europeana"),
    SPRINGER("springer"),
    EUROPEPMC("europepmc"),
    ELSEVIER("elsevier");

    private final String name;
}
