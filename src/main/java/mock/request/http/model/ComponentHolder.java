package mock.request.http.model;

import com.intellij.uiDesigner.core.GridConstraints;
import mock.request.core.constant.Constants;
import mock.request.core.utils.PropertiesUtil;
import mock.request.core.utils.StringUtils;
import mock.request.http.boot.Environment;
import mock.request.http.gui.form.MainWindow;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 组件持有者, 持有组件及当前页面激活的组件
 * @author guokai
 * @version 1.0
 */
public class ComponentHolder {

    /**
     *  组件主GUI, 方便获取各组件
     */
    private MainWindow mainWindow;

    // 当前页面在哪个页签  Params/Headers/Body...
    private JPanel currentParamsJPanel;

    // 当前页面显示的JTable, 用作工具栏添加和删除按钮
    private JTable currentJTable;

    // 当前Body选择哪种组件  none/form-data/urlencoded/raw/binary
    private JComponent currentBodyComponent;

    // body中选中的table, 可能为空, 为空没有表格
    private JTable currentBodyTable;

    // body选择的单选按钮
    private JRadioButton currentBodyTypeJRadioButton;

    // 全填充限制条件
    private final GridConstraints fillConstraints = new GridConstraints();
    {
        fillConstraints.setFill(GridConstraints.FILL_BOTH);
    }

    /**
     * body页签内的所有组件, 方便动态切换
     */
    private final Map<String, JComponent> bodyComponents = new HashMap<>();

    /**
     * 各params页签当前的JTable地址
     */
    private final Map<String, JTable> paramsJPaneActiveJTable = new HashMap<>();

    private final Map<String, JTable> bodyJTableMap = new HashMap<>();

    private final Map<String, JRadioButton> bodyJRadioButtons = new HashMap<>();

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * 保存headers/body/等页签索引
     */
    private final Map<String, Integer> paramsPaneIndexMap = new HashMap<>();

    public Map<String, Integer> getParamsPaneIndexMap() {
        return paramsPaneIndexMap;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        // 将body页签设置到内存中, 方便动态切换
        bodyComponents.put(Constants.BODY_TYPE_FORM, mainWindow.getFormDataJScrollPane());
        bodyComponents.put(Constants.BODY_TYPE_URLENCODED, mainWindow.getUrlEncodedJScrollPane());
        bodyComponents.put(Constants.BODY_TYPE_RAW, mainWindow.getRawJTextArea());
        bodyComponents.put(Constants.BODY_TYPE_BINARY, mainWindow.getBinaryJPanel());
        bodyComponents.put(Constants.BODY_TYPE_NONE, new JPanel());

        paramsJPaneActiveJTable.put(Constants.PARAMS_J_PANEL_PARAMS, mainWindow.getParamsTable());
        paramsJPaneActiveJTable.put(Constants.PARAMS_J_PANEL_HEADERS, mainWindow.getHeadersTable());

        // body中的table类型, 方便存取 (form-data/urlencoded), 用类型的key, 是为了统一
        bodyJTableMap.put(Constants.BODY_TYPE_FORM, mainWindow.getFormDataJTable());
        bodyJTableMap.put(Constants.BODY_TYPE_URLENCODED, mainWindow.getUrlEncodedJTable());

        bodyJRadioButtons.put(Constants.BODY_TYPE_NONE, mainWindow.getNoneRadioButton());
        bodyJRadioButtons.put(Constants.BODY_TYPE_FORM, mainWindow.getFormDataRadioButton());
        bodyJRadioButtons.put(Constants.BODY_TYPE_URLENCODED, mainWindow.getxWwwFormUrlencodedRadioButton());
        bodyJRadioButtons.put(Constants.BODY_TYPE_RAW, mainWindow.getRawRadioButton());
        bodyJRadioButtons.put(Constants.BODY_TYPE_BINARY, mainWindow.getBinaryRadioButton());

        paramsPaneIndexMap.put(Constants.PARAMS_J_PANEL_PARAMS, 0);
        paramsPaneIndexMap.put(Constants.PARAMS_J_PANEL_HEADERS, 1);
        paramsPaneIndexMap.put(Constants.PARAMS_J_PANEL_BODY, 2);
        paramsPaneIndexMap.put(Constants.PARAMS_J_PANEL_COOKIES, 3);
    }

    public JPanel getCurrentParamsJPanel() {
        return currentParamsJPanel;
    }

    public void setCurrentParamsJPanel(JPanel currentParamsJPanel) {
        this.currentParamsJPanel = currentParamsJPanel;
    }

    public Map<String, JTable> getParamsJPaneActiveJTable() {
        return paramsJPaneActiveJTable;
    }

    public JTable getCurrentJTable() {
        return currentJTable;
    }

    public void setCurrentJTable(JTable currentJTable) {
        this.currentJTable = currentJTable;
    }

    public Map<String, JComponent> getBodyComponents() {
        return bodyComponents;
    }

    public GridConstraints getFillConstraints() {
        return fillConstraints;
    }

    public Map<String, JTable> getBodyJTableMap() {
        return bodyJTableMap;
    }

    public JComponent getCurrentBodyComponent() {
        return currentBodyComponent;
    }

    public void setCurrentBodyComponent(JComponent currentBodyComponent) {
        this.currentBodyComponent = currentBodyComponent;
    }

    public JTable getCurrentBodyTable() {
        return currentBodyTable;
    }

    public void setCurrentBodyTable(JTable currentBodyTable) {
        this.currentBodyTable = currentBodyTable;
    }

    public JRadioButton getCurrentBodyTypeJRadioButton() {
        return currentBodyTypeJRadioButton;
    }

    public void setCurrentBodyTypeJRadioButton(JRadioButton currentBodyTypeJRadioButton) {
        this.currentBodyTypeJRadioButton = currentBodyTypeJRadioButton;
    }

    public Map<String, JRadioButton> getBodyJRadioButtons() {
        return bodyJRadioButtons;
    }
}
