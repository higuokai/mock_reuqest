package mock.request.http.model;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *  展示Tool Window属性
 * @author guokai
 * @version 1.0
 */
public class ToolWindowRunProfile implements RunProfile {

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return "HTTP";
    }

    @Override
    public @Nullable Icon getIcon() {
        return null;
    }
}
