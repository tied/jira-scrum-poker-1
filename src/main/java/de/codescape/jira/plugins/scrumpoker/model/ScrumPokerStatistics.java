package de.codescape.jira.plugins.scrumpoker.model;

/**
 * The {@link ScrumPokerStatistics} object holds all statistics that are queried and get displayed on the Scrum Poker
 * statistics page.
 */
public class ScrumPokerStatistics {

    private long sessionsTotal;
    private long votesTotal;
    private long sessionsParticipated;

    /**
     * Set the total number of Scrum Poker sessions started.
     */
    public void setSessionsTotal(long sessionsTotal) {
        this.sessionsTotal = sessionsTotal;
    }

    /**
     * Return the total number of Scrum Poker sessions started.
     */
    public long getSessionsTotal() {
        return sessionsTotal;
    }

    /**
     * Set the total number of votes provided.
     */
    public void setVotesTotal(long votesTotal) {
        this.votesTotal = votesTotal;
    }

    /**
     * Return the total number of votes provided.
     */
    public long getVotesTotal() {
        return votesTotal;
    }

    /**
     * Set the number of sessions participated in.
     */
    public void setSessionsParticipated(long sessionsParticipated) {
        this.sessionsParticipated = sessionsParticipated;
    }

    /**
     * Return the number of sessions participated in.
     */
    public long getSessionsParticipated() {
        return sessionsParticipated;
    }

}
