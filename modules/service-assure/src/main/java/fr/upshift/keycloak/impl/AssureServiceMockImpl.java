package fr.upshift.keycloak.impl;

import fr.upshift.keycloak.spi.AssureService;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class AssureServiceMockImpl implements AssureService {

    /*
     *  Lecture adhérent
     */
    @Override
    public void getAdherent(String numAdherent) {
        log.debugf("Start getAdherent(%s)", numAdherent);
    }

    /*
     *  Lecture assuré
     */
    @Override
    public void getAssure(String numAdherent) {
        log.debugf("Start getAssure(%s)", numAdherent);
    }

    @Override
    public void close() {
        // NOOP
    }

}
