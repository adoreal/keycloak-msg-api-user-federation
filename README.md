# Keycloak Microsoft Graph API User Federation

Keycloak user storage provider for allowing user federation via the Microsoft Graph API.

To make Keycloak recognize the user storage provider the src/main/resources/META-INF folder and its content is required.

## Build

Requirements are Maven (verified 3.6.3) and Java (verified openjdk 1.8.0_322).

To build a .jar file that can be used in Keycloak run the following command

```bash
mvn clean package
```

## Deploy

To deploy the user storage provider in Keycloak copy the .jar file into the `/opt/keycloak/providers` folder.

When deploying to Docker, copy the file before running `kc.sh build` in the Docker file.

## Adoreal fork

Branch `adoreal/26.3.3`, tag `v26.3.3-adoreal.2`, based on upstream `v26.2.5` (the first upstream
version after it needs Keycloak 26.4+ APIs). Built against Keycloak 26.3.3 for the `adoreal-marketing`
realm (adoreal-keycloak, ADR-0130). Three options, each defaulting to the upstream behaviour so the
patches can be offered upstream as they are:

| Key | Default | Adoreal sets | Effect |
|---|---|---|---|
| `removeMissingUsers` | `true` | `false` | A user missing from the directory is **disabled and signed out** instead of deleted; groups and history are kept. |
| `importUpnDomains` | empty | `adoreal.com` | Only **Member** accounts whose UPN or mail is on a listed domain are imported; guests and other domains are skipped. Applies to the group-members path and the all-users path. |
| `validateAuthority` | `true` | `true` | OFF skips Microsoft's instance discovery, for a private or test authority. Used by adoreal-keycloak's integration tests against a stub. |
| `syncErrorEmail` | empty | empty | Where a failing sync is reported by mail. Upstream mailed its own address; empty now sends nothing (the admin event and the server log remain). |

Build and test: `mvn -B package` (Java 17, Maven 3.9); the shaded jar is `target/msg-api-user-federation-<version>.jar`.
Unit tests: `AdorealPatchesTest`. Upstream: https://github.com/RiskManagementGroup/keycloak-msg-api-user-federation
