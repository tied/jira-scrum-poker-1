package de.codescape.jira.plugins.scrumpoker.action;

import com.atlassian.jira.junit.rules.AvailableInContainer;
import com.atlassian.jira.junit.rules.MockitoContainer;
import com.atlassian.jira.junit.rules.MockitoMocksInContainer;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.web.HttpServletVariables;
import de.codescape.jira.plugins.scrumpoker.model.GlobalSettings;
import de.codescape.jira.plugins.scrumpoker.service.GlobalSettingsService;
import de.codescape.jira.plugins.scrumpoker.service.ProjectSettingsService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ProjectSettingsActionTest {

    @Rule
    public MockitoContainer mockitoContainer = MockitoMocksInContainer.rule(this);

    @Mock
    private GlobalSettingsService globalSettingsService;

    @Mock
    private ProjectSettingsService projectSettingsService;

    @Mock
    private ProjectManager projectManager;

    @Mock
    @AvailableInContainer
    private HttpServletVariables httpServletVariables;

    @InjectMocks
    private ProjectSettingsAction projectSettingsAction;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private GlobalSettings globalSettings;

    @Mock
    private Project project;

    @Test
    public void shouldExposeTheProjectKeyWhenCalled() {
        when(httpServletVariables.getHttpRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getParameter(ProjectSettingsAction.Parameters.PROJECT_KEY)).thenReturn("ABC");
        projectSettingsAction.doExecute();
        assertThat(projectSettingsAction.getProjectKey(), is(equalTo("ABC")));
    }

    @Test
    public void shouldReturnGlobalActivateScrumPokerFlag() {
        when(globalSettingsService.load()).thenReturn(globalSettings);
        when(globalSettings.isActivateScrumPoker()).thenReturn(true);
        assertThat(projectSettingsAction.isActivateScrumPokerGlobally(), is(true));
    }

    @Test
    public void shouldReturnProjectSpecificActivateScrumPokerFlag() {
        when(httpServletVariables.getHttpRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getParameter(ProjectSettingsAction.Parameters.PROJECT_KEY)).thenReturn("ABC");

        projectSettingsAction.doExecute();

        when(projectManager.getProjectByCurrentKey(eq("ABC"))).thenReturn(project);
        when(project.getId()).thenReturn(42L);
        when(projectSettingsService.loadActivateScrumPoker(eq(42L))).thenReturn(true);

        assertThat(projectSettingsAction.isActivateScrumPokerForProject(), is(true));
    }

}
