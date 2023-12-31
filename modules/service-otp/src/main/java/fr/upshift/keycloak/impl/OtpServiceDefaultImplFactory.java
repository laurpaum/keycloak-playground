package fr.upshift.keycloak.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.google.auto.service.AutoService;

import fr.upshift.keycloak.spi.OtpService;
import fr.upshift.keycloak.spi.OtpServiceFactory;

@AutoService(OtpServiceFactory.class)
public class OtpServiceDefaultImplFactory implements OtpServiceFactory {

    public static final String PROVIDER_ID = "service-otp-default";

    private static final OtpServiceDefaultImpl SINGLETON = new OtpServiceDefaultImpl();

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public OtpService create(KeycloakSession session) {
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
