package de.codescape.jira.plugins.scrumpoker.condition;

import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.user.ApplicationUser;
import de.codescape.jira.plugins.scrumpoker.service.GlobalSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This condition is used to decide whether the Scrum Poker dropdown shall be shown on the boards view that is
 * introduced by Jira Software. This is one possible entry point to the active sessions list for example.
 */
@Component
public class ScrumPokerDropdownOnBoardCondition extends AbstractWebCondition {

    private final GlobalSettingsService globalSettingsService;

    @Autowired
    public ScrumPokerDropdownOnBoardCondition(GlobalSettingsService globalSettingsService) {
        this.globalSettingsService = globalSettingsService;
    }

    @Override
    public boolean shouldDisplay(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        return globalSettingsService.load().isDisplayDropdownOnBoards();
    }

}
