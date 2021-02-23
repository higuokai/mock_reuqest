package mock.request.http.boot;

import mock.request.http.model.ComponentHolder;
import mock.request.http.model.GUIPackageWorker;
import mock.request.http.model.worker.ChangeWorker;
import mock.request.http.model.worker.RuntimeChangeTextWorker;
import mock.request.http.model.worker.SettingFileWorker;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 环境配置
 * @author guokai
 * @version 1.0
 */
public class Environment {

    private static Environment environment;

    private GUIPackageWorker guiPackageWorker;

    private Map<String, JComponent> bodyJTablesMap;

    private ChangeWorker changeWorker;

    private ComponentHolder componentHolder;

    private RuntimeChangeTextWorker runtimeChangeTextWorker;

    private final AtomicBoolean toolWindowLoadSuccess = new AtomicBoolean(false);

    public ComponentHolder getComponentHolder() {
        return componentHolder;
    }

    public SettingFileWorker settingFileWorker;

    public SettingFileWorker getSettingFileWorker() {
        return settingFileWorker;
    }

    public void setSettingFileWorker(SettingFileWorker settingFileWorker) {
        this.settingFileWorker = settingFileWorker;
    }

    public void setComponentHolder(ComponentHolder componentHolder) {
        this.componentHolder = componentHolder;
    }

    public static Environment getInstance() {
        if (environment == null) {
            synchronized (Environment.class) {
                if (environment == null) {
                    environment = new Environment();
                }
            }
        }
        return environment;
    }

    public GUIPackageWorker getGuiPackageWorker() {
        return guiPackageWorker;
    }

    public void setGuiPackageWorker(GUIPackageWorker guiPackageWorker) {
        this.guiPackageWorker = guiPackageWorker;
    }

    public Map<String, JComponent> getBodyJTablesMap() {
        return bodyJTablesMap;
    }

    public void setBodyJTablesMap(Map<String, JComponent> bodyJTablesMap) {
        this.bodyJTablesMap = bodyJTablesMap;
    }

    public ChangeWorker getChangeWorker() {
        return changeWorker;
    }

    public void setChangeWorker(ChangeWorker changeWorker) {
        this.changeWorker = changeWorker;
    }

    public AtomicBoolean getToolWindowLoadSuccess() {
        return toolWindowLoadSuccess;
    }

    public RuntimeChangeTextWorker getRuntimeChangeTextWorker() {
        return runtimeChangeTextWorker;
    }

    public void setRuntimeChangeTextWorker(RuntimeChangeTextWorker runtimeChangeTextWorker) {
        this.runtimeChangeTextWorker = runtimeChangeTextWorker;
    }
}
