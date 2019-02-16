package de.codescape.jira.plugins.scrumpoker.rest.mapper;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import de.codescape.jira.plugins.scrumpoker.ao.ScrumPokerSession;
import de.codescape.jira.plugins.scrumpoker.ao.ScrumPokerVote;
import de.codescape.jira.plugins.scrumpoker.model.AllowRevealDeck;
import de.codescape.jira.plugins.scrumpoker.rest.entities.SessionEntity;
import de.codescape.jira.plugins.scrumpoker.service.ScrumPokerSettingService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class SessionEntityMapperTest {

    private static final String CURRENT_USER = "CURRENT_USER";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private UserManager userManager;

    @Mock
    private ScrumPokerSettingService scrumPokerSettingService;

    @InjectMocks
    private SessionEntityMapper sessionEntityMapper;

    @Mock
    private ApplicationUser applicationUser;

    @Before
    public void before() {
        when(userManager.getUserByKey(anyString())).thenReturn(applicationUser);
        when(scrumPokerSettingService.loadAllowRevealDeck()).thenReturn(AllowRevealDeck.EVERYONE);
        when(applicationUser.getDisplayName()).thenReturn("John Doe");
    }

    @Test
    public void shouldNotReturnAgreementForHiddenDeck() {
        ScrumPokerVote[] scrumPokerVotes = {scrumPokerVote("5"), scrumPokerVote("5")};
        ScrumPokerSession scrumPokerSession = scrumPokerSession(scrumPokerVotes, false);

        SessionEntity sessionEntity = sessionEntityMapper.build(scrumPokerSession, CURRENT_USER);
        assertThat(sessionEntity.isAgreementReached(), is(false));
    }

    @Test
    public void shouldReturnAgreementForVisibleDeck() {
        ScrumPokerVote[] scrumPokerVotes = {scrumPokerVote("5"), scrumPokerVote("5")};
        ScrumPokerSession scrumPokerSession = scrumPokerSession(scrumPokerVotes, true);

        SessionEntity sessionEntity = sessionEntityMapper.build(scrumPokerSession, CURRENT_USER);
        assertThat(sessionEntity.isAgreementReached(), is(true));
    }

    @Test
    public void shouldNotReturnAgreementForVisibleDeckWithDifferentVotes() {
        ScrumPokerVote[] scrumPokerVotes = {scrumPokerVote("5"), scrumPokerVote("3")};
        ScrumPokerSession scrumPokerSession = scrumPokerSession(scrumPokerVotes, true);

        SessionEntity sessionEntity = sessionEntityMapper.build(scrumPokerSession, CURRENT_USER);
        assertThat(sessionEntity.isAgreementReached(), is(false));
    }

    @Test
    public void shouldReturnBoundedVotesWithCountForEachVoteInRange() {
        ScrumPokerVote[] scrumPokerVotes = {
            scrumPokerVote("5"),
            scrumPokerVote("5"),
            scrumPokerVote("13")};
        ScrumPokerSession scrumPokerSession = scrumPokerSession(scrumPokerVotes, true);

        SessionEntity sessionEntity = sessionEntityMapper.build(scrumPokerSession, CURRENT_USER);
        assertThat(sessionEntity.getBoundedVotes().size(), is(equalTo(3)));
        assertThat(sessionEntity.getBoundedVotes(), allOf(
            hasItem(allOf(hasProperty("value", equalTo(5)), hasProperty("count", equalTo(2)))),
            hasItem(allOf(hasProperty("value", equalTo(8)), hasProperty("count", equalTo(0)))),
            hasItem(allOf(hasProperty("value", equalTo(13)), hasProperty("count", equalTo(1))))
        ));
    }

    @Test
    public void isAllowRevealShouldDependOnCurrentVisibilityOfDeck() {
        reset(scrumPokerSettingService);
        when(scrumPokerSettingService.loadAllowRevealDeck()).thenReturn(AllowRevealDeck.EVERYONE);
        ScrumPokerVote[] scrumPokerVotes = {scrumPokerVote("5")};

        ScrumPokerSession hiddenScrumPokerSession = scrumPokerSession(scrumPokerVotes, false);
        assertThat(sessionEntityMapper.build(hiddenScrumPokerSession, CURRENT_USER).isAllowReveal(), is(true));

        ScrumPokerSession visibleScrumPokerSession = scrumPokerSession(scrumPokerVotes, true);
        assertThat(sessionEntityMapper.build(visibleScrumPokerSession, CURRENT_USER).isAllowReveal(), is(false));
    }

    @Test
    public void isAllowRevealShouldDependOnExistenceOfVotes() {
        reset(scrumPokerSettingService);
        when(scrumPokerSettingService.loadAllowRevealDeck()).thenReturn(AllowRevealDeck.EVERYONE);

        ScrumPokerVote[] oneScrumPokerVote = {scrumPokerVote("5")};
        ScrumPokerSession scrumPokerSessionWithVotes = scrumPokerSession(oneScrumPokerVote, false);
        assertThat(sessionEntityMapper.build(scrumPokerSessionWithVotes, CURRENT_USER).isAllowReveal(), is(true));

        ScrumPokerVote[] noScrumPokerVote = {};
        ScrumPokerSession scrumPokerSessionWithoutVotes = scrumPokerSession(noScrumPokerVote, false);
        assertThat(sessionEntityMapper.build(scrumPokerSessionWithoutVotes, CURRENT_USER).isAllowReveal(), is(false));
    }

    @Test
    public void isAllowRevealShouldDependOnAllowRevealSettingForSettingEveryone() {
        reset(scrumPokerSettingService);
        when(scrumPokerSettingService.loadAllowRevealDeck()).thenReturn(AllowRevealDeck.EVERYONE);
        ScrumPokerVote[] scrumPokerVotesWithoutUser = {scrumPokerVote("5", "ANOTHER_USER"), scrumPokerVote("8", "ANOTHER_USER_2")};

        ScrumPokerSession scrumPokerSessionWithVotes = scrumPokerSession(scrumPokerVotesWithoutUser, false);
        assertThat(sessionEntityMapper.build(scrumPokerSessionWithVotes, CURRENT_USER).isAllowReveal(), is(true));
    }

    @Test
    public void isAllowRevealShouldDependOnAllowRevealSettingForSettingCreator() {
        reset(scrumPokerSettingService);
        when(scrumPokerSettingService.loadAllowRevealDeck()).thenReturn(AllowRevealDeck.CREATOR);
        ScrumPokerVote[] scrumPokerVotesWithoutUser = {scrumPokerVote("5", "ANOTHER_USER")};

        ScrumPokerSession sessionWhereCurrentUserIsCreator = scrumPokerSession(scrumPokerVotesWithoutUser, false, CURRENT_USER);
        assertThat(sessionEntityMapper.build(sessionWhereCurrentUserIsCreator, CURRENT_USER).isAllowReveal(), is(true));

        ScrumPokerSession sessionWhereCurrentUserIsNotCreator = scrumPokerSession(scrumPokerVotesWithoutUser, false, "ANOTHER_USER");
        assertThat(sessionEntityMapper.build(sessionWhereCurrentUserIsNotCreator, CURRENT_USER).isAllowReveal(), is(false));
    }

    @Test
    public void isAllowRevealShouldDependOnAllowRevealSettingForSettingParticipant() {
        reset(scrumPokerSettingService);
        when(scrumPokerSettingService.loadAllowRevealDeck()).thenReturn(AllowRevealDeck.PARTICIPANTS);

        ScrumPokerVote[] scrumPokerVotesWithoutUser = {scrumPokerVote("5", "ANOTHER_USER")};
        ScrumPokerSession sessionWhereCurrentUserIsCreator = scrumPokerSession(scrumPokerVotesWithoutUser, false, CURRENT_USER);
        assertThat(sessionEntityMapper.build(sessionWhereCurrentUserIsCreator, CURRENT_USER).isAllowReveal(), is(false));

        ScrumPokerVote[] scrumPokerVotesWithUser = {scrumPokerVote("5", "ANOTHER_USER"), scrumPokerVote("3", CURRENT_USER)};
        ScrumPokerSession sessionWhereCurrentUserIsNotCreator = scrumPokerSession(scrumPokerVotesWithUser, false, "ANOTHER_USER");
        assertThat(sessionEntityMapper.build(sessionWhereCurrentUserIsNotCreator, CURRENT_USER).isAllowReveal(), is(true));
    }

    private ScrumPokerSession scrumPokerSession(ScrumPokerVote[] scrumPokerVotes, boolean visible) {
        return scrumPokerSession(scrumPokerVotes, visible, "CREATOR_USER");
    }

    private ScrumPokerSession scrumPokerSession(ScrumPokerVote[] scrumPokerVotes, boolean visible, String creatorUserKey) {
        ScrumPokerSession scrumPokerSession = mock(ScrumPokerSession.class);
        when(scrumPokerSession.getVotes()).thenReturn(scrumPokerVotes);
        when(scrumPokerSession.getCreateDate()).thenReturn(new Date());
        when(scrumPokerSession.getCreatorUserKey()).thenReturn(creatorUserKey);
        when(scrumPokerSession.getIssueKey()).thenReturn("ISSUE-1");
        when(scrumPokerSession.isVisible()).thenReturn(visible);
        return scrumPokerSession;
    }

    private ScrumPokerVote scrumPokerVote(String value) {
        return scrumPokerVote(value, "ANOTHER_USER");
    }

    private ScrumPokerVote scrumPokerVote(String value, String userKey) {
        ScrumPokerVote vote = mock(ScrumPokerVote.class);
        when(vote.getVote()).thenReturn(value);
        when(vote.getUserKey()).thenReturn(userKey);
        return vote;
    }

}