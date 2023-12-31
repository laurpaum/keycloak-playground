package fr.upshift.keycloak.auth;

import java.util.Set;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import fr.upshift.keycloak.spi.OtpService;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class IdentityVerification implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        log.info("Start authenticate");

        UserModel user = context.getUser();
        if (user == null) {
            log.warn("User is null");
            context.attempted(); return;
        }

        KeycloakSession session = context.getSession();

        Set<String> ids = session.listProviderIds(OtpService.class);
        log.infof("Got %d ids:", ids.size());
        ids.stream().forEach(p -> log.infof(" %s", p));

        Set<OtpService> providers = session.getAllProviders(OtpService.class);
        log.infof("Got %d providers:", providers.size());
        providers.stream().forEach(p -> log.infof(" %s", p.getClass()));

        OtpService serviceOtp = session.getProvider(OtpService.class);
        if (serviceOtp == null) {
            log.warn("OTP service is null");
            context.attempted(); return;
        }

        context.success();
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        log.info("Start action");
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // NOOP
    }

    @Override
    public void close() {
        // NOOP
    }

}
