package io.wiremock.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class Run {

    public static void main(String[] args) {
        var wireMockServer = new WireMockServer(WireMockConfiguration
                .wireMockConfig()
                .port(8000)
                .extensionScanningEnabled(true));

        wireMockServer.start();
    }
}
