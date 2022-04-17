package com.example.rest;

import com.example.service.ServiceAProxy;
import com.example.shared.configuration.ModuleConfig;
import com.example.shared.configuration.PropertyName;
import com.example.shared.exceptions.RemoteServiceException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ServiceARestProxy implements ServiceAProxy {

    private final String url;

    public ServiceARestProxy() {
        this.url = ModuleConfig.loadConfiguration().getStringList(PropertyName.of("service.url.a")).get(0);
    }

    @Override
    public int calculateSum(int a, int b) {
        URI uri = URI.create(url + "?a=" + a + "&b=" + b);
        HttpRequest build = HttpRequest.newBuilder().uri(uri)
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> send = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
            if (send.statusCode() != 200) {
                throw new RemoteServiceException(send.statusCode(),
                        send.request().uri());
            }
            return Integer.parseInt(send.body());
        } catch (IOException e) {
            throw new RemoteServiceException("Unable to call " + uri, e);
        } catch (InterruptedException e) {
            throw new RemoteServiceException("Interrupted call to " + uri, e);
        }
    }
}
