package mock.request.http.gui.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import mock.request.http.boot.Environment;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author guokai
 * @version 1.0
 */
public class ToolbarDeleteAction extends AnAction {

    public ToolbarDeleteAction() {
        super("Delete", "Delete", AllIcons.General.HideToolWindow);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JTable activeJTable = Environment.getInstance().getComponentHolder().getCurrentJTable();
        if (activeJTable != null) {
            int selectedRow = activeJTable.getSelectedRow();
            if (selectedRow > -1) {
                DefaultTableModel model = (DefaultTableModel) activeJTable.getModel();
                model.removeRow(selectedRow);
            }
        }
    }
}
