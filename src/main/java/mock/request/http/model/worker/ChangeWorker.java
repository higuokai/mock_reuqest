package mock.request.http.model.worker;

import com.intellij.ide.util.PropertiesComponent;
import mock.request.core.constant.Constants;
import mock.request.core.lang.MockMap;
import mock.request.core.utils.PropertiesUtil;
import mock.request.core.utils.StringUtils;
import mock.request.http.boot.Environment;
import mock.request.http.gui.form.MainWindow;
import mock.request.http.model.ComponentHolder;
import mock.request.http.util.JsonDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.util.Map;

/**
 * @author guokai
 * @version 1.0
 */
public class ChangeWorker {

    private final ComponentHolder componentHolder = Environment.getInstance().getComponentHolder();

    private String lastResultTypeValue;

    /**
     * 修改HttpMethod
     */
    public void updateHttpMethod() {
        JComboBox<String> httpMethodBox = componentHolder.getMainWindow().getHttpMethodBox();
        if (httpMethodBox == null) {
            return;
        }
        String method = httpMethodBox.getSelectedItem().toString();
        PropertiesUtil.set(Constants.PROPERTIES_KEY_HTTP_METHOD, method);
    }

    /**
     * 修改URL值
     */
    public void updateUrl() {
        JTextField urlField = componentHolder.getMainWindow().getUrlField();
        if (urlField == null) {
            return;
        }
        String url = urlField.getText();
        PropertiesUtil.set(Constants.PROPERTIES_KEY_URL, url);
    }

    /**
     * 修改当前页面显示的参数JPanel  (params/headers/body之间切换)
     */
    public void changeCurrentParamsJPanel() {
        Component selectedComponent = componentHolder.getMainWindow().getParamsPane().getSelectedComponent();
        String componentName = selectedComponent.getName();
        // 将选择的持久化
        PropertiesUtil.set(Constants.PROPERTIES_KEY_PARAM_PANEL, componentName);
        componentHolder.setCurrentParamsJPanel((JPanel)selectedComponent);
        // 同时伴随currentTable变化
        this.changeCurrentJTable(componentName);

        PropertiesUtil.set(Constants.PROPERTIES_KEY_PARAM_PANEL, componentName);
    }

    /**
     * 修改body 参数类型单选
     * @param key   类型
     */
    public void changeBodyRadioButton(String key) {
        // 改变holder中单选按钮
        componentHolder.setCurrentBodyTypeJRadioButton(componentHolder.getBodyJRadioButtons().get(key));
        // 改变body内容展示
        changeBodyJPanelVisible(key);
        // 改变holder中body当前组件
        componentHolder.setCurrentBodyComponent(componentHolder.getBodyComponents().get(key));
        // 改变holder中currentTable
        // body是否要修改 currentTable
        changeBodyCurrentJTable(key);
        // 改变当前table
        changeCurrentJTable(Constants.PARAMS_J_PANEL_BODY);

        PropertiesUtil.set(Constants.PROPERTIES_KEY_BODY_RADIO, key);
    }

    private void changeBodyCurrentJTable(String key) {
        // 从bodyJTable中获取
        JTable jTable = componentHolder.getBodyJTableMap().get(key);
        // 不管等不等于null, 放入map中, 这里的key是body页签的key, 让改变页签时可以取到
        componentHolder.getParamsJPaneActiveJTable().put(Constants.PARAMS_J_PANEL_BODY, jTable);
        componentHolder.setCurrentBodyTable(jTable);
    }

    /**
     * 改变body内容页签显示隐藏
     * @param key   要展示的类型
     */
    private void changeBodyJPanelVisible(String key) {
        Map<String, JComponent> bodyComponents = componentHolder.getBodyComponents();
        JPanel bodyParentPanel = componentHolder.getMainWindow().getBodyParentPanel();
        // 删除全部
        bodyParentPanel.removeAll();
        JComponent jComponent = bodyComponents.get(key);
        if (jComponent != null) {
            bodyParentPanel.add(jComponent, componentHolder.getFillConstraints());
        }
        // 重绘页面
        bodyParentPanel.validate();
        bodyParentPanel.repaint();
    }

    /**
     * 改变当前table, 根据选择的 Pane页签选择JTable
     * @param key   paramsPanel、headersPanel 等
     */
    private void changeCurrentJTable(String key) {
        Map<String, JTable> activeJTable = componentHolder.getParamsJPaneActiveJTable();
        JTable jTable = activeJTable.get(key);
        // 设置
        componentHolder.setCurrentJTable(jTable);
    }

    /**
     * 修改返回值类型标签
     * @param editable  是否可编辑
     */
    public void updateResultType(boolean editable) {
        MainWindow mainWindow = componentHolder.getMainWindow();
        // 保存原先的值
        JComboBox<String> resultTypeComboBox = mainWindow.getResultTypeComboBox();
        if (editable) {
            lastResultTypeValue = resultTypeComboBox.getSelectedItem().toString();
            resultTypeComboBox.setSelectedItem("File");
        } else {
            resultTypeComboBox.setSelectedItem(lastResultTypeValue);
        }
        resultTypeComboBox.setEnabled(!editable);
    }

    private void changeParamsJPanelByJPanelName(String componentName) {
        // 从集合中获取并选中
        Integer index = componentHolder.getParamsPaneIndexMap().get(componentName);
        if (index != null) {
            // 选中
            componentHolder.getMainWindow().getParamsPane().setSelectedIndex(index);
            // 切换当前table
            this.changeCurrentJTable(componentName);
        }
    }

    /**
     * 默认的方法前提是已经选中, 初始化时没有选中, 设置一个初始值
     * @param key body type key
     */
    private void selectBodyRadioAndChangeValue(String key) {
        Map<String, JRadioButton> bodyJRadioButtons = componentHolder.getBodyJRadioButtons();
        JRadioButton jRadioButton = bodyJRadioButtons.get(key);
        if (jRadioButton == null) {
            key = Constants.BODY_TYPE_NONE;
            jRadioButton = bodyJRadioButtons.get(key);
        }
        jRadioButton.setSelected(true);
        this.changeBodyRadioButton(key);
    }

    /**
     * 刷新当前组件内容
     */
    public void refreshCurrentComponent(MockMap data) {
        // 获取保存的内容
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        // httpMethod
        String method = propertiesComponent.getValue(Constants.PROPERTIES_KEY_HTTP_METHOD);
        // url
        String url = propertiesComponent.getValue(Constants.PROPERTIES_KEY_URL);
        // 那个paramsJPanel标签
        String paramPanel = propertiesComponent.getValue(Constants.PROPERTIES_KEY_PARAM_PANEL);
        // body选中 参数类型
        String bodyRadio = propertiesComponent.getValue(Constants.PROPERTIES_KEY_BODY_RADIO);

        MainWindow mainWindow = componentHolder.getMainWindow();
        if (StringUtils.isNotEmpty(method)) {
            mainWindow.getHttpMethodBox().setSelectedItem(method);
        }

        if (StringUtils.isNotEmpty(url)) {
            mainWindow.getUrlField().setText(url);
        }

        this.selectBodyRadioAndChangeValue(bodyRadio);

        if (StringUtils.isNotEmpty(paramPanel)) {
            // 切换页签
            this.changeParamsJPanelByJPanelName(paramPanel);
        }
    }

    public void updateRawType(MainWindow mainWindow, DocumentListener listener) {
        JComboBox<String> rawTypeComboBox = mainWindow.getRawTypeComboBox();
        String selectType = rawTypeComboBox.getSelectedItem().toString();
        Document document = mainWindow.getRawJTextArea().getDocument();
        if ("json".equals(selectType.toLowerCase())) {
            // 添加监听
            document.addDocumentListener(listener);
        } else {
            // 删除监听
            document.removeDocumentListener(listener);
        }
    }
}
