package mock.request.http.boot;

import com.intellij.ide.util.PropertiesComponent;
import mock.request.core.constant.Constants;
import mock.request.core.utils.PropertiesUtil;
import mock.request.core.utils.StringUtils;
import mock.request.http.gui.form.MainWindow;
import mock.request.http.model.ComponentHolder;
import mock.request.http.model.worker.ChangeWorker;
import mock.request.http.model.worker.SettingFileWorker;

/**
 * @author guokai
 * @version 1.0
 */
public class ApplicationContext {

    /**
     * 数据准备
     */
    public static void prepareEnvironment() {
        Environment environment = Environment.getInstance();
        ChangeWorker changeWorker = environment.getChangeWorker();

        SettingFileWorker worker = new SettingFileWorker();
        environment.setSettingFileWorker(worker);
        worker.refreshSetting();

        // 刷新组件初始化值
        changeWorker.refreshCurrentComponent(null);

    }

}
