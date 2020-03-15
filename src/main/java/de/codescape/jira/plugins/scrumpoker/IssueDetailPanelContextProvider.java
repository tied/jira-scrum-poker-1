package de.codescape.jira.plugins.scrumpoker;

import com.atlassian.jira.datetime.DateTimeFormatter;
import com.atlassian.jira.datetime.DateTimeStyle;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import de.codescape.jira.plugins.scrumpoker.ao.ScrumPokerSession;
import de.codescape.jira.plugins.scrumpoker.service.ScrumPokerSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Context provider preparing information required to display data for recent or active Scrum Poker session on the Jira
 * issue detail page with option to join and participate on a currently running Scrum Poker session.
 */
@Component
public class IssueDetailPanelContextProvider extends AbstractJiraContextProvider {

    private final UserManager userManager;
    private final DateTimeFormatter dateTimeFormatter;
    private final ScrumPokerSessionService scrumPokerSessionService;

    @Autowired
    public IssueDetailPanelContextProvider(@ComponentImport UserManager userManager,
                                           @ComponentImport DateTimeFormatter dateTimeFormatter,
                                           ScrumPokerSessionService scrumPokerSessionService) {
        this.userManager = userManager;
        this.dateTimeFormatter = dateTimeFormatter;
        this.scrumPokerSessionService = scrumPokerSessionService;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Map getContextMap(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        Map<String, Object> contextMap = new HashMap<>();
        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue");
        contextMap.put("issueKey", currentIssue.getKey());
        contextMap.put("contextPath", jiraHelper.getRequest().getContextPath());

        ScrumPokerSession scrumPokerSession = scrumPokerSessionService.recent().stream()
            .filter(session -> session.getIssueKey().equals(currentIssue.getKey()))
            .findFirst().orElse(null);

        if (scrumPokerSession != null) {
            contextMap.put("found", true);
            contextMap.put("started", relativeDate(scrumPokerSession.getCreateDate()));
            contextMap.put("startedBy", displayName(scrumPokerSession.getCreatorUserKey()));
            contextMap.put("participants", scrumPokerSession.getVotes().length);
            if (scrumPokerSession.getConfirmedVote() == null && !scrumPokerSession.isCancelled()) {
                contextMap.put("running", true);
            } else {
                contextMap.put("running", false);
                contextMap.put("cancelled", scrumPokerSession.isCancelled());
                contextMap.put("estimate", scrumPokerSession.getConfirmedVote());
                contextMap.put("finished", relativeDate(scrumPokerSession.getConfirmedDate()));
            }
        }
        return contextMap;
    }

    private String relativeDate(Date date) {
        return dateTimeFormatter.withStyle(DateTimeStyle.RELATIVE).format(date);
    }

    private String displayName(String key) {
        if (key == null) {
            return null;
        }
        ApplicationUser user = userManager.getUserByKey(key);
        return user != null ? user.getDisplayName() : key;
    }

}
