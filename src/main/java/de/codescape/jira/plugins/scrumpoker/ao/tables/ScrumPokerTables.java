package de.codescape.jira.plugins.scrumpoker.ao.tables;

import static de.codescape.jira.plugins.scrumpoker.ScrumPokerConstants.ACTIVE_OBJECTS_TABLE_PREFIX;

/**
 * Entry point for building queries with Querydsl with the Active Objects tables.
 */
public final class ScrumPokerTables {

    public static final ScrumPokerVoteTable SCRUM_POKER_VOTE = new ScrumPokerVoteTable(ACTIVE_OBJECTS_TABLE_PREFIX + "SCRUM_POKER_VOTE");

    private ScrumPokerTables() {
        throw new AssertionError("ScrumPokerTables must not be instantiated.");
    }

}
