package mock.request.http.gui.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import mock.request.http.boot.ApplicationContext;
import mock.request.http.boot.Environment;
import mock.request.http.model.GUIPackageWorker;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  Tools Menu弹出Action
 * @author guokai
 * @version 1.0
 */
public class MockPostmanToolsAction extends AnAction {

    /**
     * 弹出ToolWindow, 同时是入口
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Environment environment = Environment.getInstance();
        AtomicBoolean loadSuccess = environment.getToolWindowLoadSuccess();
        if (loadSuccess.get()) {
            // 加载完成,直接展示
            environment.getGuiPackageWorker().showGUI();
        } else {
            try {
                // 组装GUI
                GUIPackageWorker guiPackageWorker = new GUIPackageWorker(e.getProject());
                guiPackageWorker.buildGUI();

                // 数据准备
                ApplicationContext.prepareEnvironment();

                // 组装完成,展示窗口
                guiPackageWorker.showGUI();
                environment.setGuiPackageWorker(guiPackageWorker);
                loadSuccess.compareAndSet(false, true);
            } catch (Throwable cause) {
                cause.printStackTrace();
                throw cause;
            }
        }
    }
}
