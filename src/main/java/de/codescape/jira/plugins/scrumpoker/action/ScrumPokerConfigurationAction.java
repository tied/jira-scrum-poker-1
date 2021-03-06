package de.codescape.jira.plugins.scrumpoker.action;

import com.atlassian.jira.issue.fields.CustomField;
import de.codescape.jira.plugins.scrumpoker.model.AllowRevealDeck;
import de.codescape.jira.plugins.scrumpoker.model.DisplayCommentsForIssue;
import de.codescape.jira.plugins.scrumpoker.model.GlobalSettings;
import de.codescape.jira.plugins.scrumpoker.service.EstimateFieldService;
import de.codescape.jira.plugins.scrumpoker.service.GlobalSettingsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Global configuration of the Scrum Poker plugin.
 */
public class ScrumPokerConfigurationAction extends AbstractScrumPokerAction {

    private static final long serialVersionUID = 1L;

    /**
     * Names of all parameters used on the global configuration page.
     */
    static final class Parameters {

        static final String ACTION = "action";
        static final String ESTIMATE_FIELD = "estimateField";
        static final String SESSION_TIMEOUT = "sessionTimeout";
        static final String ACTIVATE_SCRUM_POKER = "activateScrumPoker";
        static final String ALLOW_REVEAL_DECK = "allowRevealDeck";
        static final String DISPLAY_DROPDOWN_ON_BOARDS = "displayDropdownOnBoards";
        static final String CHECK_PERMISSION_TO_SAVE_ESTIMATE = "checkPermissionToSaveEstimate";
        static final String DISPLAY_COMMENTS_FOR_ISSUE = "displayCommentsForIssue";
        static final String CARD_SET = "cardSet";

    }

    private final EstimateFieldService estimateFieldService;
    private final GlobalSettingsService globalSettingsService;

    @Autowired
    public ScrumPokerConfigurationAction(EstimateFieldService estimateFieldService,
                                         GlobalSettingsService globalSettingsService) {
        this.estimateFieldService = estimateFieldService;
        this.globalSettingsService = globalSettingsService;
    }

    /**
     * List of all custom fields currently available in this Jira instance.
     */
    public List<CustomField> getCustomFields() {
        return estimateFieldService.supportedCustomFields();
    }

    /**
     * Return the currently set global settings.
     */
    public GlobalSettings getGlobalSettings() {
        return globalSettingsService.load();
    }

    /**
     * Save the global settings if the form is saved and submitted.
     */
    @Override
    protected String doExecute() {
        String action = getParameter(Parameters.ACTION);
        if (action != null) {
            if (action.equals("save")) {
                GlobalSettings globalSettings = new GlobalSettings();

                String newEstimateField = getParameter(Parameters.ESTIMATE_FIELD);
                globalSettings.setEstimateField(newEstimateField);

                String newSessionTimeout = getParameter(Parameters.SESSION_TIMEOUT);
                globalSettings.setSessionTimeout(Integer.valueOf(newSessionTimeout));

                String newActivateScrumPoker = getParameter(Parameters.ACTIVATE_SCRUM_POKER);
                globalSettings.setActivateScrumPoker(Boolean.parseBoolean(newActivateScrumPoker));

                String newAllowRevealDeck = getParameter(Parameters.ALLOW_REVEAL_DECK);
                globalSettings.setAllowRevealDeck(AllowRevealDeck.valueOf(newAllowRevealDeck));

                String displayDropdownOnBoards = getParameter(Parameters.DISPLAY_DROPDOWN_ON_BOARDS);
                globalSettings.setDisplayDropdownOnBoards(Boolean.parseBoolean(displayDropdownOnBoards));

                String checkPermissionToSaveEstimate = getParameter(Parameters.CHECK_PERMISSION_TO_SAVE_ESTIMATE);
                globalSettings.setCheckPermissionToSaveEstimate(Boolean.parseBoolean(checkPermissionToSaveEstimate));

                String displayCommentsForIssue = getParameter(Parameters.DISPLAY_COMMENTS_FOR_ISSUE);
                globalSettings.setDisplayCommentsForIssue(DisplayCommentsForIssue.valueOf(displayCommentsForIssue));

                String cardSet = getParameter(Parameters.CARD_SET);
                globalSettings.setCardSet(cardSet);

                globalSettingsService.persist(globalSettings);
            } else if (action.equals("defaults")) {
                globalSettingsService.persist(new GlobalSettings());
            }
        }
        return SUCCESS;
    }

}
