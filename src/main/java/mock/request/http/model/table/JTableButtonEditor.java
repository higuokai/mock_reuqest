package mock.request.http.model.table;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.*;
import java.util.EventObject;

/**
 * @author guokai
 * @version 1.0
 */
public class JTableButtonEditor extends DefaultCellEditor {

    private JPanel panel;

    private JButton button;

    private Project project;

    private JTable jTable;

    private int row = -1;

    private int column = -1;

    public JTableButtonEditor() {
        super(new JTextField());
        this.setClickCountToStart(1);
        this.initButton();
        this.initPanel();
        // 添加按钮。
        this.panel.add(this.button);
    }

    private void initButton() {
        this.button = new JButton();

        // 设置按钮的大小及位置。
        this.button.setBounds(0, 0, 50, 20);

        // 为按钮添加事件。这里只能添加ActionListener事件，Mouse事件无效。
        this.button.addActionListener(e -> {
            // 选择文件
            FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true,false,true,true,true,false);

            VirtualFile virtualFile = FileChooser.chooseFile(chooserDescriptor, project, null);
            if(virtualFile == null) {
                // 选择结束
                return;
            }
            String path = virtualFile.getPath();
            jTable.getModel().setValueAt(path, row, column-2);
            this.jTable = null;
            this.row = -1;
            this.column = -1;
        });

    }

    private void initPanel() {
        this.panel = new JPanel();
        // panel使用绝对定位，这样button就不会充满整个单元格。
        this.panel.setLayout(null);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    /**
     * 这里重写父类的编辑方法，返回一个JPanel对象即可（也可以直接返回一个Button对象，但是那样会填充满整个单元格）
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.jTable = table;
        this.row = row;
        this.column = column;
        button.setText("上传");
        return this.panel;
    }

    /**
     * 重写编辑单元格时获取的值。如果不重写，这里可能会为按钮设置错误的值。
     */
    @Override
    public Object getCellEditorValue() {
        return this.button.getText();
    }

}
