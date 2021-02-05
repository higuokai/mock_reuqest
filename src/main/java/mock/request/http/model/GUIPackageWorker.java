package mock.request.http.model;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.Executor;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.content.Content;
import mock.request.core.utils.IconUtil;
import mock.request.http.gui.executors.CustomRunExecutor;
import mock.request.http.gui.form.MainWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author guokai
 * @version 1.0
 */
public class GUIPackageWorker implements Disposable {

    private final Project project;

    RunContentDescriptor descriptor;

    private Executor executor;

    public GUIPackageWorker(@NotNull Project project) {
        this.project = project;
    }

    public void buildGUI() {
        if (project.isDisposed()) {
            return;
        }

        executor = CustomRunExecutor.getRunExecutorInstance();
        if (executor == null) {
            return;
        }

        final RunnerLayoutUi.Factory factory = RunnerLayoutUi.Factory.getInstance(project);
        RunnerLayoutUi layoutUi = factory.create("mock.postman.runnerId", "mock.postman.runnerTitle", "mock.postman.sessionName", project);
        // 窗口描述
        descriptor = new RunContentDescriptor(new ToolWindowRunProfile(), new DefaultExecutionResult(), layoutUi);
        descriptor.setExecutionId(System.nanoTime());

        // 获取GUI内容
        MainWindow mainWindow = new MainWindow();
        JPanel guiContent = mainWindow.getContent();

        // 向布局写入内容
        final Content content = layoutUi.createContent("contentId", guiContent, "Http请求", IconUtil.icon(), guiContent);
        // 组装按钮栏
        layoutUi.getOptions().setLeftToolbar(mainWindow.createActionToolbar(), "RunnerToolbar");

        content.setCloseable(false);
        layoutUi.addContent(content);
        Disposer.register(descriptor,this);
    }

    public void showGUI() {
        ExecutionManager.getInstance(project).getContentManager().showRunContent(executor, descriptor);
    }

    @Override
    public void dispose() {
        Disposer.dispose(this);
    }
}
