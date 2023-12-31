package fr.upshift.keycloak.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

import com.google.auto.service.AutoService;

@AutoService(Spi.class)
public class OtpSpi implements Spi {

    public static final String PROVIDER_ID = "service-otp";

    @Override
    public boolean isInternal() {
        return false;
    }
    
    @Override
    public String getName() {
        return PROVIDER_ID;
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return OtpService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return OtpServiceFactory.class;
    }

}
