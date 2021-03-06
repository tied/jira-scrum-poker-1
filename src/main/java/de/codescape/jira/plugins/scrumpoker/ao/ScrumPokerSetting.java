package de.codescape.jira.plugins.scrumpoker.ao;

import net.java.ao.Preload;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.StringLength;

/**
 * Active Object to persist a Scrum Poker settings into the database.
 */
@Preload
public interface ScrumPokerSetting extends ScrumPokerEntity {

    /**
     * The unique key of the Scrum Poker setting.
     */
    @NotNull
    @Indexed
    @StringLength(255)
    String getKey();

    void setKey(String key);

    /**
     * The value of the Scrum Poker setting.
     */
    @StringLength(value = StringLength.UNLIMITED)
    String getValue();

    void setValue(String value);

}

