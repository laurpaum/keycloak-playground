package fr.upshift.keycloak.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.google.auto.service.AutoService;

import fr.upshift.keycloak.spi.AssureService;
import fr.upshift.keycloak.spi.AssureServiceFactory;

@AutoService(AssureServiceFactory.class)
public class AssureServiceDefaultImplFactory implements AssureServiceFactory {
    
    public static final String PROVIDER_ID = "assure-default";

    private static final AssureServiceDefaultImpl SINGLETON = new AssureServiceDefaultImpl();

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public AssureService create(KeycloakSession session) {
        return SINGLETON;
    }

    @Override
    public void init(Scope config) {
        // NOOP
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // NOOP
    }

    @Override
    public void close() {
        // NOOP
    }

    @Override
    public int order() {
        return 0;
    }

}
