package de.codescape.jira.plugins.scrumpoker.action;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import de.codescape.jira.plugins.scrumpoker.model.ScrumPokerStatistics;
import de.codescape.jira.plugins.scrumpoker.service.ScrumPokerSessionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Show some statistics for the currently logged in user.
 */
// TODO implement tests
public class ShowStatisticsAction extends AbstractScrumPokerAction {

    private final ScrumPokerSessionService scrumPokerSessionService;
    private final JiraAuthenticationContext jiraAuthenticationContext;

    private ScrumPokerStatistics statistics;

    @Autowired
    public ShowStatisticsAction(ScrumPokerSessionService scrumPokerSessionService,
                                @ComponentImport JiraAuthenticationContext jiraAuthenticationContext) {
        this.scrumPokerSessionService = scrumPokerSessionService;
        this.jiraAuthenticationContext = jiraAuthenticationContext;
    }

    /**
     * Just show the page.
     */
    @Override
    protected String doExecute() {
        statistics = scrumPokerSessionService.statistics(currentUser().getKey());
        return SUCCESS;
    }

    /**
     * Returns the statistics object.
     */
    public ScrumPokerStatistics getStatistics() {
        return statistics;
    }

    private ApplicationUser currentUser() {
        return jiraAuthenticationContext.getLoggedInUser();
    }

}
