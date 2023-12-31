# keycloak-extensions

cd /opt/jboss/keycloak/

bin/add-user-keycloak.sh -u admin -p admin

for f in modules/service-otp/target/*.jar ; do docker cp $f keycloak:/tmp/ ; done

bin/jboss-cli.sh --command="module add --name=fr.upshift.keycloak.service-otp --resources=/tmp/service-otp-1.0.0-SNAPSHOT.jar --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,org.jboss.logging"

bin/jboss-cli.sh --connect --command="/subsystem=keycloak-server:list-add(name=providers,value=module:fr.upshift.keycloak.service-otp)"

bin/jboss-cli.sh --connect --command="/subsystem=logging/logger=fr.upshift:add(category=fr.upshift,level=DEBUG,use-parent-handlers=true)"

bin/jboss-cli.sh --connect --command=reload

for f in modules/auth-demo/target/*.jar ; do docker cp $f keycloak:/opt/jboss/keycloak/standalone/deployments/ ; done

