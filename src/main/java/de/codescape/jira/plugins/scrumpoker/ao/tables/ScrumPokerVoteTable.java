package de.codescape.jira.plugins.scrumpoker.ao.tables;

import com.atlassian.pocketknife.spi.querydsl.EnhancedRelationalPathBase;
import com.querydsl.core.types.dsl.StringPath;

/**
 * Representation of the database table for the database table based on the Active Objects object
 * {@link de.codescape.jira.plugins.scrumpoker.ao.ScrumPokerVote}.
 */
public class ScrumPokerVoteTable extends EnhancedRelationalPathBase<ScrumPokerVoteTable> {

    public final StringPath VOTE = createStringCol("VOTE").build();
    public final StringPath USER_KEY = createStringCol("USER_KEY").build();

    public ScrumPokerVoteTable(String tableName) {
        super(ScrumPokerVoteTable.class, tableName);
    }

}
