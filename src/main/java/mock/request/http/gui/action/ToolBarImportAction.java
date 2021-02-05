package mock.request.http.gui.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import mock.request.http.boot.Environment;
import mock.request.http.model.worker.SettingFileWorker;
import org.jetbrains.annotations.NotNull;

/**
 * 导入配置文件按钮, 应该通知settingFileListener解析或改变值
 * @author guokai
 * @version 1.0
 */
public class ToolBarImportAction extends AnAction {

    public ToolBarImportAction() {
        super("导入请求配置", "Import", AllIcons.ToolbarDecorator.Import);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 弹出文件选择器
        FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true,false,false,false,false,false);
        VirtualFile virtualFile = FileChooser.chooseFile(chooserDescriptor, e.getProject(), null);
        if(virtualFile == null) {
            // 选择结束
            return;
        }
        SettingFileWorker settingFileWorker = Environment.getInstance().getSettingFileWorker();
        settingFileWorker.importSettingFile(virtualFile);
    }
}
