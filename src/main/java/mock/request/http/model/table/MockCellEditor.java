package mock.request.http.model.table;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.util.EventObject;

/**
 * 自定义单元格编辑器
 * @author guokai
 * @version 1.0
 */
public class MockCellEditor extends DefaultCellEditor {

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }



    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        // 不允许选中
        return false;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    public MockCellEditor(JTextField textField) {
        super(textField);
    }

}
