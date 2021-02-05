package mock.request.http.gui.executors;

import com.intellij.execution.Executor;
import com.intellij.execution.ExecutorRegistry;
import com.intellij.openapi.util.NlsActions;
import mock.request.core.constant.Constants;
import mock.request.core.utils.IconUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 *  定义toolWindow静态信息
 * @author guokai
 * @version 1.0
 */
public class CustomRunExecutor extends Executor{

    /**
     *  tool window id
     */
    @Override
    public @NotNull String getToolWindowId() {
        return Constants.TOOL_WINDOW_ID;
    }
    /**
     *  图标
     */
    @Override
    public @NotNull Icon getToolWindowIcon() {
        return IconUtil.icon();
    }
    /**
     *  图标
     */
    @Override
    public @NotNull Icon getIcon() {
        return IconUtil.icon();
    }
    /**
     *  不可用图标
     */
    @Override
    public Icon getDisabledIcon() {
        return IconUtil.icon();
    }

    @Override
    public @NlsActions.ActionDescription String getDescription() {
        return Constants.MOCK_POSTMAN_ACTION;
    }

    @Override
    public @NotNull @NlsActions.ActionText String getActionName() {
        return Constants.MOCK_POSTMAN_ACTION_TEXT;
    }

    /**
     * 插件ID
     */
    @Override
    public @NotNull String getId() {
        return Constants.PLUGIN_ID;
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getStartActionText() {
        return Constants.MOCK_POSTMAN_ACTION_TEXT;
    }

    @Override
    public String getContextActionId() {
        return "context action id";
    }

    @Override
    public String getHelpId() {
        return Constants.TOOL_WINDOW_ID;
    }

    public static Executor getRunExecutorInstance() {
        return ExecutorRegistry.getInstance().getExecutorById(Constants.PLUGIN_ID);
    }

}
