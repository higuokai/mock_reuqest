package mock.request.http.model;

import javax.swing.*;
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

    public MockCellEditor(JTextField textField) {
        super(textField);
    }
}
