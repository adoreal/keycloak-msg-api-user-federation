package dk.rmgroup.keycloak.storage.api.msg;

public final class MsgApiUserStorageProviderConstants {
    public static final String CONFIG_KEY_AUTHORITY = "authority";
    public static final String CONFIG_KEY_MSG_BASE_URL = "msgBaseUrl";
    public static final String CONFIG_KEY_CLIENT_ID = "clientId";
    public static final String CONFIG_KEY_SECRET = "secret";
    public static final String CONFIG_KEY_SCOPE = "scope";
    public static final String CONFIG_KEY_ALLOW_UPDATE_UPN_DOMAINS = "allowUpdateUpnDomains";
    public static final String CONFIG_KEY_GROUP_MAP = "groupMap";
    public static final String CONFIG_KEY_IMPORT_USERS_NOT_IN_MAPPED_GROUPS = "importUsersNotInMappedGroups";
    public static final String CONFIG_KEY_GROUPS_FOR_USERS_NOT_IN_MAPPED_GROUPS = "groupsForUsersNotInMappedGroups";
    public static final String CONFIG_DEFAULT_AUTHORITY = "https://login.microsoftonline.com/Enter_the_Tenant_Id_Here/";
    public static final String CONFIG_DEFAULT_MSG_URL = "https://graph.microsoft.com/v1.0/";
    public static final String CONFIG_DEFAULT_SCOPE = "https://graph.microsoft.com/.default";
    // adoreal fork (README "Adoreal fork"): three options, each defaulting to upstream behaviour.
    public static final String CONFIG_KEY_REMOVE_MISSING_USERS = "removeMissingUsers";
    public static final String CONFIG_KEY_IMPORT_UPN_DOMAINS = "importUpnDomains";
    public static final String CONFIG_KEY_SYNC_ERROR_EMAIL = "syncErrorEmail";
    public static final String CONFIG_KEY_VALIDATE_AUTHORITY = "validateAuthority";
}
