package fr.upshift.keycloak.spi;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.provider.Provider;

public interface OtpService extends Provider {

    public void generateCode(AuthenticationFlowContext context);

    public void verifyCode(AuthenticationFlowContext context, String formCode);

}
