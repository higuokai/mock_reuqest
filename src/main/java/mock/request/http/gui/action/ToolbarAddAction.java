package mock.request.http.gui.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import mock.request.http.boot.Environment;
import mock.request.http.model.ComponentHolder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *  侧边添加按钮
 * @author guokai
 * @version 1.0
 */
public class ToolbarAddAction extends AnAction {

    public ToolbarAddAction() {
        super("Add", "ADD", AllIcons.General.Add);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 获取当前table
        JTable activeJTable = Environment.getInstance().getComponentHolder().getCurrentJTable();
        if (activeJTable != null) {
            // 不管哪个table, 直接添加一个空白
            DefaultTableModel tableModel = (DefaultTableModel) activeJTable.getModel();
            tableModel.addRow(new Object[]{Boolean.FALSE, "", ""});
        }
    }
}
