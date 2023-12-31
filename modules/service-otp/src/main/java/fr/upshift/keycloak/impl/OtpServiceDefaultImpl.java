package fr.upshift.keycloak.impl;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.sessions.AuthenticationSessionModel;

import fr.upshift.keycloak.exception.CodeExpiredException;
import fr.upshift.keycloak.exception.CodeMismatchException;
import fr.upshift.keycloak.exception.TooManyRetriesException;
import fr.upshift.keycloak.spi.OtpService;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class OtpServiceDefaultImpl implements OtpService {

    private static final String AUTH_NOTE_OTP_CODE = "OTP_CODE";
    private static final String AUTH_NOTE_OTP_EXPIRES = "OTP_EXPIRES";
    private static final String AUTH_NOTE_OTP_RETRIES = "OTP_RETRIES";

    private static final int OTP_LENGTH = 6;
    private static final int OTP_TTL_MN = 3;
    private static final int OTP_RETRIES = 3;

    /*
     *  Génération du code OTP
     */
    @Override
    public void generateCode(AuthenticationFlowContext context) {
        String otpCode = SecretGenerator.getInstance().randomString(OTP_LENGTH, SecretGenerator.DIGITS);
        String otpExpires = Long.toString(System.currentTimeMillis() + 60000L * OTP_TTL_MN);
        String otpRetries = Integer.toString(OTP_RETRIES);

        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        authSession.setAuthNote(AUTH_NOTE_OTP_CODE, otpCode);
        authSession.setAuthNote(AUTH_NOTE_OTP_EXPIRES, otpExpires);
        authSession.setAuthNote(AUTH_NOTE_OTP_RETRIES, otpRetries);

        log.debugf("Generated authentication code : %s", otpCode);
    }

    /*
     *  Vérification du code OTP
     */
    @Override
    public void verifyCode(AuthenticationFlowContext context, String formCode) {
        log.debugf("Verifying code : %s", formCode);

        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        String otpCode = (String) authSession.getAuthNote(AUTH_NOTE_OTP_CODE);
        String otpExpires = (String) authSession.getAuthNote(AUTH_NOTE_OTP_EXPIRES);
        String otpRetries = (String) authSession.getAuthNote(AUTH_NOTE_OTP_RETRIES);

        if (System.currentTimeMillis() > Long.parseLong(otpExpires)) {
            log.debugf("Code is expired");
            throw new CodeExpiredException();
        }

        if ("0".equals(otpRetries)) {
            log.debugf("Too many retries");
            throw new TooManyRetriesException();
        }

        if (!formCode.equals(otpCode)) {
            otpRetries = Integer.toString(Integer.parseInt(otpRetries) - 1);
            authSession.setAuthNote(AUTH_NOTE_OTP_RETRIES, otpRetries);
            if ("0".equals(otpRetries)) {
                log.debugf("Too many retries");
                throw new TooManyRetriesException();
            } else {
                log.debugf("Invalid code");
                throw new CodeMismatchException();
            }
        }

        log.debugf("Code is valid");
    }

    @Override
    public void close() {
        // NOOP
    }

}
