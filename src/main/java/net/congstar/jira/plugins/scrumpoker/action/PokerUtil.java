package net.congstar.jira.plugins.scrumpoker.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.congstar.jira.plugins.scrumpoker.model.PokerCard;

public class PokerUtil {
	
    public static PokerCard[] pokerDeck = {new PokerCard("q", "q.jpg"),
            new PokerCard("0", "0.jpg"),
            new PokerCard("0.5", "05.jpg"),
            new PokerCard("1", "1.jpg"),
            new PokerCard("2", "2.jpg"),
            new PokerCard("3", "3.jpg"),
            new PokerCard("5", "5.jpg"),
            new PokerCard("8", "8.jpg"),
            new PokerCard("13", "13.jpg"),
            new PokerCard("20", "20.jpg"),
            new PokerCard("40", "40.jpg"),
            new PokerCard("100", "100.jpg")};
    
    /**
     * Returns all cards from the deck from the minimum vote to the maximum vote
     * @param issueCards
     * @return
     */
    public static Collection<String> getBoundedVotes(Map<String, String> issueCards) {
        ArrayList<String> votes = new ArrayList<String>();
        for (BigDecimal value : getSortedVotes(issueCards)) {
            votes.add(value.toString());
        }
        ArrayList<String> boundedVotes = new ArrayList<String>();

        String first = votes.get(0);
        String last = votes.get(votes.size() - 1);

        if (votes.size() > 0) {

            int index = 0;
            while (!pokerDeck[index].getName().equals(first)) {
                index++;
            }
            boundedVotes.add(pokerDeck[index].getName());
            if (votes.size() > 1) {
                index++;
                while (!pokerDeck[index].getName().equals(last)) {
                    boundedVotes.add(pokerDeck[index].getName());
                    index++;
                }
                boundedVotes.add(pokerDeck[index].getName());
            }
        }
        return boundedVotes;
    }

    /**
     * returns a sorted list of votes
     * @param votes
     * @return
     */
    public static Set<BigDecimal> getSortedVotes(Map<String, String> votes) {
        Collection<String> votedValues = votes.values();
        Set<BigDecimal> uniqueValues = new TreeSet<BigDecimal>();
        for (String value : votedValues) {
            if (!value.equals("q")) {
                uniqueValues.add(new BigDecimal(value));
            }
        }
        return uniqueValues;
    }

}
