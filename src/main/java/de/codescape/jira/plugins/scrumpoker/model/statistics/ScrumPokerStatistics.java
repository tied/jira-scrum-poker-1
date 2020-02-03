package de.codescape.jira.plugins.scrumpoker.model.statistics;

import java.util.List;

/**
 * The {@link ScrumPokerStatistics} object holds all statistics that are queried and get displayed on the Scrum Poker
 * statistics page.
 */
public class ScrumPokerStatistics {

    private long sessionsTotal;
    private long votesTotal;
    private long sessionsParticipated;
    private List<VoteDistribution> globalVotesDistribution;
    private List<VoteDistribution> personalVotesDistribution;

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

    /**
     * Set the global distribution of votes.
     */
    public void setGlobalVotesDistribution(List<VoteDistribution> globalVotesDistribution) {
        this.globalVotesDistribution = globalVotesDistribution;
    }

    /**
     * Return the global distribution of votes.
     */
    public List<VoteDistribution> getGlobalVotesDistribution() {
        return globalVotesDistribution;
    }

    /**
     * Set the personal distribution of votes.
     */
    public void setPersonalVotesDistribution(List<VoteDistribution> personalVotesDistribution) {
        this.personalVotesDistribution = personalVotesDistribution;
    }

    /**
     * Return the personal distribution of votes.
     */
    public List<VoteDistribution> getPersonalVotesDistribution() {
        return personalVotesDistribution;
    }

}
