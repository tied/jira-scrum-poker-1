package de.codescape.jira.plugins.scrumpoker.action;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import de.codescape.jira.plugins.scrumpoker.model.ScrumPokerStatistics;
import de.codescape.jira.plugins.scrumpoker.service.ScrumPokerSessionService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import webwork.action.Action;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ShowStatisticsActionTest {

    private static final String USER_KEY = "SOME_USER";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private JiraAuthenticationContext jiraAuthenticationContext;

    @Mock
    private ScrumPokerSessionService scrumPokerSessionService;

    @InjectMocks
    private ShowStatisticsAction showStatisticsAction;

    @Mock
    private ApplicationUser applicationUser;

    @Mock
    private ScrumPokerStatistics scrumPokerStatistics;

    @Test
    public void whenOpeningPageStatisticsAreRequestedOnce() {
        when(jiraAuthenticationContext.getLoggedInUser()).thenReturn(applicationUser);
        when(applicationUser.getKey()).thenReturn(USER_KEY);
        when(scrumPokerSessionService.statistics(eq(USER_KEY))).thenReturn(scrumPokerStatistics);

        assertThat(showStatisticsAction.doExecute(), is(equalTo(Action.SUCCESS)));
        verify(scrumPokerSessionService, times(1)).statistics(anyString());

        assertThat(showStatisticsAction.getStatistics(), is(equalTo(scrumPokerStatistics)));
        verify(scrumPokerSessionService, times(1)).statistics(anyString());
    }

}
