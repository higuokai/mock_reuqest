package mock.request.http.model.worker;

import com.intellij.openapi.vfs.VirtualFile;
import mock.request.core.constant.Constants;
import mock.request.core.utils.PropertiesUtil;
import mock.request.core.utils.StringUtils;
import mock.request.http.boot.Environment;

import javax.swing.*;

/**
 * 配置文件工具类, 解析文件, 并负责历史记录组装并刷新
 * @author guokai
 * @version 1.0
 */
public class SettingFileWorker {

    /**
     * 初始化配置
     */
    public void refreshSetting() {
        String location = PropertiesUtil.get(Constants.PROPERTIES_KEY_SETTING_FILE_LOCATION);
        JLabel locationLabel =
                Environment.getInstance().getComponentHolder().getMainWindow().getSettingFileLocationLabel();
        if (StringUtils.isEmpty(location)) {
            locationLabel.setText("请选择配置文件位置!");
            return;
        }

        // 读取文件


    }

    public void importSettingFile(VirtualFile virtualFile) {
        String path = virtualFile.getPath();
        JLabel locationLabel =
                Environment.getInstance().getComponentHolder().getMainWindow().getSettingFileLocationLabel();
        locationLabel.setText(path);
    }

}
