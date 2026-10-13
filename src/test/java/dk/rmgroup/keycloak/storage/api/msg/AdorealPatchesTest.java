package dk.rmgroup.keycloak.storage.api.msg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Test;

/** The adoreal fork's import filter (README "Adoreal fork"). */
public class AdorealPatchesTest {

  private static JSONObject row(String upn, String mail, String userType) {
    JSONObject o = new JSONObject();
    o.put("userPrincipalName", upn);
    o.put("mail", mail);
    if (userType != null) {
      o.put("userType", userType);
    }
    return o;
  }

  private static final List<String> ADOREAL = Collections.singletonList("adoreal.com");

  @Test
  public void parseDomains_trims_lowercases_and_drops_blanks() {
    assertEquals(Arrays.asList("adoreal.com", "example.org"),
        MsgApiUserStorageProviderFactory.parseDomains(" Adoreal.com, ,example.ORG "));
    assertTrue(MsgApiUserStorageProviderFactory.parseDomains(null).isEmpty());
    assertTrue(MsgApiUserStorageProviderFactory.parseDomains("  ").isEmpty());
  }

  @Test
  public void without_domains_every_row_with_a_mail_is_imported_as_upstream_does() {
    assertTrue(MsgApiUserStorageProviderFactory.isImportable(row("x_other.com#EXT#@t.onmicrosoft.com", "x@other.com", "Guest"),
        Collections.emptyList()));
    assertFalse(MsgApiUserStorageProviderFactory.isImportable(row("nomail@adoreal.com", "", "Member"), Collections.emptyList()));
  }

  @Test
  public void with_domains_only_members_on_the_domain_are_imported() {
    assertTrue(MsgApiUserStorageProviderFactory.isImportable(row("Alice@Adoreal.com", "alice@adoreal.com", "Member"), ADOREAL));
    assertTrue("mail on the domain is enough when the UPN is not",
        MsgApiUserStorageProviderFactory.isImportable(row("alice@adoreal.onmicrosoft.com", "alice@adoreal.com", "Member"), ADOREAL));
    assertTrue("rows without userType are members (Graph omits it when not selected)",
        MsgApiUserStorageProviderFactory.isImportable(row("bob@adoreal.com", "bob@adoreal.com", null), ADOREAL));
    assertFalse("guest", MsgApiUserStorageProviderFactory.isImportable(
        row("x_other.com#EXT#@adoreal.onmicrosoft.com", "x@adoreal.com", "Guest"), ADOREAL));
    assertFalse("other domain", MsgApiUserStorageProviderFactory.isImportable(row("c@other.com", "c@other.com", "Member"), ADOREAL));
    assertFalse("no mail", MsgApiUserStorageProviderFactory.isImportable(row("d@adoreal.com", "", "Member"), ADOREAL));
  }

  @Test
  public void only_handle_groups_in_group_map_keeps_groups_the_sync_does_not_manage() {
    Set<String> managed = new HashSet<>(Arrays.asList("mapped-group"));
    assertTrue("a mapped group may be left", MsgApiUserStorageProviderFactory.mayLeave("mapped-group", managed, true));
    assertFalse("realm-owners is not the sync's to remove", MsgApiUserStorageProviderFactory.mayLeave("realm-owners", managed, true));
    assertFalse("with an empty map nothing is ever removed", MsgApiUserStorageProviderFactory.mayLeave("realm-owners", new HashSet<>(), true));
    assertTrue("upstream behaviour when the option is off", MsgApiUserStorageProviderFactory.mayLeave("realm-owners", managed, false));
  }
}
