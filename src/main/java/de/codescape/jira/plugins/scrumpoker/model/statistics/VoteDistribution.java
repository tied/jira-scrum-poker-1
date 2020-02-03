package de.codescape.jira.plugins.scrumpoker.model.statistics;

/**
 * Data transfer object to represent a vote and the number of times this vote was presented by a user.
 */
public class VoteDistribution {

    private String vote;
    private Long count;

    /**
     * Construct a vote distribution object.
     *
     * @param vote  vote
     * @param count number of times vote was presented
     */
    public VoteDistribution(String vote, Long count) {
        this.vote = vote;
        this.count = count;
    }

    /**
     * Return the vote.
     */
    public String getVote() {
        return vote;
    }

    /**
     * Return the number of times vote was presented.
     */
    public Long getCount() {
        return count;
    }

}
