package de.codescape.jira.plugins.scrumpoker;

/**
 * Globally used constants that are reused among Scrum Poker classes go into this class.
 */
public final class ScrumPokerConstants {

    /**
     * Plugin key that identifies the Scrum Poker for Jira plugin.
     */
    public static final String SCRUM_POKER_PLUGIN_KEY = "de.codescape.jira.plugins.scrum-poker";

    /**
     * Active Objects table prefix calculated from the MD5 hash of the plugin key name.
     */
    public static final String ACTIVE_OBJECTS_TABLE_PREFIX = "AO_1FA2A8_";

    // Prevent utility class from being instantiated
    private ScrumPokerConstants() {
        throw new AssertionError("ScrumPokerConstants must not be instantiated.");
    }

}
