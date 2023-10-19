package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Statistics {
    private final int maxRequestsPerSecond;
    private final int averageRequestsPerSecond;
}
