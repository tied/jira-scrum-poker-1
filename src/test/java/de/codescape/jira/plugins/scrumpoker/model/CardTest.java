package de.codescape.jira.plugins.scrumpoker.model;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CardTest {

    @Test
    public void cardsDifferIfAssignableFlagIsDifferent() {
        assertThat(new Card("1", true), is(not(equalTo(new Card("1", false)))));
    }

    @Test
    public void cardsDifferIfValueIsDifferent() {
        assertThat(new Card("1", true), is(not(equalTo(new Card("3", true)))));
    }

    @Test
    public void cardsAreEqualIfValueAndAssignableFlagAreEqual() {
        assertThat(new Card("something", false), is(equalTo(new Card("something", false))));
    }

    @Test
    public void questionMarkShouldHaveTheCorrectDatabaseRepresentation() {
        assertThat(Card.QUESTION_MARK.getValue(), is(equalTo("question")));
    }

    @Test
    public void coffeeBreakShouldHaveTheCorrectDatabaseRepresentation() {
        assertThat(Card.COFFEE_BREAK.getValue(), is(equalTo("coffee")));
    }

}