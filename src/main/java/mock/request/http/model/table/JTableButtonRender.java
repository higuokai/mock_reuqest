package mock.request.http.model.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author guokai
 * @version 1.0
 */
public class JTableButtonRender implements TableCellRenderer {

    private JPanel panel;

    private JButton button;

    public JTableButtonRender() {
        this.initPanel();
        this.initButton();
        this.panel.add(button);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.button.setText("上传");
        return this.panel;
    }

    private void initButton() {
        this.button = new JButton();
        // 设置按钮的大小及位置。
        this.button.setBounds(0, 0, 50, 20);
    }

    private void initPanel() {
        this.panel = new JPanel();
        // panel使用绝对定位，这样button就不会充满整个单元格。
        this.panel.setLayout(null);
    }
}
