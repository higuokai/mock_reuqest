package mock.request.http.model.table;

import javax.swing.*;
import java.awt.*;
import java.util.EventObject;

/**
 * @author guokai
 * @version 1.0
 */
public class JTableCheckBoxEditor extends DefaultCellEditor {

    public JTableCheckBoxEditor(JCheckBox checkBox) {
        super(checkBox);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
