package com.faisal.howtodoinjava.howtodoinjava.config;


import com.faisal.howtodoinjava.howtodoinjava.resources.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(UserResource.class);
    }
}
