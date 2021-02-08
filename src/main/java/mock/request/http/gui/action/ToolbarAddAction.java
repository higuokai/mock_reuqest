package mock.request.http.gui.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import mock.request.core.lang.MockMap;
import mock.request.http.boot.Environment;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
            String name = activeJTable.getName();
            if ("formDataJTable".equals(name)) {
                DefaultTableModel tableModel = (DefaultTableModel) activeJTable.getModel();
                tableModel.addRow(new Object[]{Boolean.TRUE, "", "", Boolean.FALSE, null});
            } else {
                DefaultTableModel tableModel = (DefaultTableModel) activeJTable.getModel();
                tableModel.addRow(new Object[]{Boolean.TRUE, "", ""});
            }
        }
    }
}
