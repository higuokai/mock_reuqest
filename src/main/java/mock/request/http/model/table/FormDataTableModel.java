package mock.request.http.model.table;

import mock.request.core.utils.StringUtils;

import javax.swing.table.DefaultTableModel;
import java.util.Objects;

/**
 *  表单table model
 * @author guokai
 * @version 1.0
 */
public class FormDataTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        boolean editable;
        if (column != 2) {
            editable =  true;
        } else {
            editable = Objects.equals(false, this.getValueAt(row, column+1));
        }
        if ((column == 1 || column == 2) && editable) {
            // 判断下一行有没有数据
            if (this.getRowCount() == (row+1)) {
                // 正在编辑最后一行
                this.addRow(new Object[]{Boolean.FALSE, "", "", Boolean.FALSE, null});
            }
        }
        return editable;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        // 如果是第四列, 且值为false, 第三列变为空
        if (column == 3 && Objects.equals(false, aValue)) {
            this.setValueAt("", row, 2);
        }
        if ((column == 1 ||column == 2) && StringUtils.isNotEmpty(aValue)) {
            // 设置为选中
            this.setValueAt(Boolean.TRUE, row, 0);
        }
        super.setValueAt(aValue, row, column);
        // 修改内存中值
    }

}