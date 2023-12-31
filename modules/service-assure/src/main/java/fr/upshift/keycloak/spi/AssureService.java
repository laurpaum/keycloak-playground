package fr.upshift.keycloak.spi;

import org.keycloak.provider.Provider;

public interface AssureService extends Provider {

    public void getAdherent(String numAdherent);

    public void getAssure(String numAdherent);

}
