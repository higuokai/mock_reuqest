package mock.request.http.gui.form;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import mock.request.core.constant.Constants;
import mock.request.http.boot.Environment;
import mock.request.http.gui.action.ToolBarImportAction;
import mock.request.http.gui.action.ToolbarAddAction;
import mock.request.http.gui.action.ToolbarDeleteAction;
import mock.request.http.model.ComponentHolder;
import mock.request.http.model.table.*;
import mock.request.http.model.worker.ChangeWorker;
import mock.request.http.model.worker.HttpWorker;
import mock.request.http.util.JsonDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.Objects;

/**
 *  tool window主体部分
 * @author guokai
 * @version 1.0
 */
public class MainWindow {

    // 主体部分
    private JPanel myToolWindowContent;
    // 划分左右两部分的 JSplitPane
    private JSplitPane leftRightSplitPane;

    // 左侧Panel
    private JPanel leftPanel;
    // 配置文件位置 JLabel
    private JLabel settingFileLocationLabel;
    // History/Groups JTabbedPane
    private JTabbedPane leftTabledPane;
    // 历史记录的JPanel
    private JPanel historyPanel;
    // 分组
    private JPanel groupsPanel;
    private JPanel rightPanel;
    private JComboBox<String> httpMethodBox;
    private JTextField urlField;
    private JButton sendButton;
    private JCheckBox saveFileCheckBox;
    private JTabbedPane paramsPane;
    private JComboBox<String> resultTypeComboBox;
    private JSplitPane rightSplitPane;
    private JPanel paramsParentJPanel;
    private JPanel responsePanel;
    private JLabel protocolLabel;

    private JPanel paramsPanel;
    private JPanel headersPanel;
    private JPanel bodyPanel;
    private JPanel cookiesPanel;

    private JTable paramsTable;
    private JTable headersTable;

    // body
    private JRadioButton noneRadioButton;
    private JRadioButton formDataRadioButton;
    private JRadioButton xWwwFormUrlencodedRadioButton;
    private JRadioButton rawRadioButton;
    private JRadioButton binaryRadioButton;

    private JComboBox<String> rawTypeComboBox;
    private JTable formDataJTable;
    private JScrollPane formDataJScrollPane;
    private JScrollPane urlEncodedJScrollPane;
    private JTable urlEncodedJTable;
    private JTextArea rawJTextArea;
    private JButton bodyBinaryButton;
    private JPanel bodyParentPanel;
    private JPanel binaryJPanel;
    private JTextArea responseTextArea;
    private JScrollPane historyScrollPane;
    private JTree historyTree;

    /**
     * 初始化GUI
     */
    public MainWindow() {
        // 初始化组件持有器
        Environment environment = Environment.getInstance();
        ComponentHolder holder = new ComponentHolder();
        environment.setComponentHolder(holder);
        holder.setMainWindow(this);

        ChangeWorker worker = new ChangeWorker();
        environment.setChangeWorker(worker);


        // 各组件设置name/key
        setName();

        // ----------------------------------------
        // ----------------------------------------
        // ------------ 页面添加监听事件 -------------
        // ----------------------------------------
        // ----------------------------------------

        // 请求方法修改监听
        httpMethodBox.addActionListener(e -> {
            worker.updateHttpMethod();
        });

        // URL修改监听
        urlField.getDocument().addDocumentListener(new DocumentListener() {
            // 插入时监听
            @Override
            public void insertUpdate(DocumentEvent e) {
                worker.updateUrl();
            }
            // 删除值时监听
            @Override
            public void removeUpdate(DocumentEvent e) {
                worker.updateUrl();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        // params/headers/body/cookies 页签发生修改, 通知修改器修改
        paramsPane.addChangeListener(e -> {
            worker.changeCurrentParamsJPanel();
        });

        // --------------- body --------------
        // none  单选选中
        noneRadioButton.addActionListener(e -> {
            worker.changeBodyRadioButton(Constants.BODY_TYPE_NONE);
        });
        // form-data  单选选中
        formDataRadioButton.addActionListener(e -> {
            worker.changeBodyRadioButton(Constants.BODY_TYPE_FORM);
        });
        // urlencoded  单选选中
        xWwwFormUrlencodedRadioButton.addActionListener(e -> {
            worker.changeBodyRadioButton(Constants.BODY_TYPE_URLENCODED);
        });
        // binary  单选选中
        binaryRadioButton.addActionListener(e -> {
            worker.changeBodyRadioButton(Constants.BODY_TYPE_BINARY);
        });
        // raw  单选选中
        rawRadioButton.addItemListener(e -> {
            if (rawRadioButton.isSelected()) {
                worker.changeBodyRadioButton(Constants.BODY_TYPE_RAW);
                this.rawTypeComboBox.setEnabled(true);
            } else {
                this.rawTypeComboBox.setEnabled(false);
            }
        });

        saveFileCheckBox.addItemListener(e -> {
            worker.updateResultType(saveFileCheckBox.isSelected());
        });

        rawJTextArea.getDocument().addDocumentListener(new JsonDocumentListener(this.rawJTextArea));
        // ----------------------------------------
        // ----------------------------------------
        // --------- 监听添加完成,初始化页面 ----------
        // ----------------------------------------
        // ----------------------------------------

        // 左侧设置最小大小, 可以拖动到最左边
        leftPanel.setMinimumSize(new Dimension(0,0));
        // 初始化params页签JTable (添加column等)
        this.initJTable(paramsTable);
        // 初始化headers页签JTable (添加column等)
        this.initJTable(headersTable);
        // 初始化body页签 form-data JTable (添加column等)
        this.initFormDataJTable(formDataJTable);
        // 初始化body页签 urlencoded JTable (添加column等)
        this.initJTable(urlEncodedJTable);

        // 发送按钮事件监听, 通知发送Http请求
        sendButton.addActionListener(e -> {
            new HttpWorker().buildAndSend();
        });

        // 初始化历史记录
        historyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        DefaultMutableTreeNode historyRoot = new DefaultMutableTreeNode("History");
        ((DefaultTreeModel)historyTree.getModel()).setRoot(historyRoot);
        historyTree.expandPath(new TreePath(historyRoot));

        // 树选中事件
        historyTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getPath();
                System.out.println(path);
            }
        });
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }

    /**
     * 侧边栏按钮
     * @return actionGroup 按钮组
     */
    public ActionGroup createActionToolbar() {
        final DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new ToolbarAddAction());
        actionGroup.add(new ToolbarDeleteAction());
        actionGroup.add(new ToolBarImportAction());
        return actionGroup;
    }

    /**
     * 初始化JTable, 添加Column和editor等
     * @param currentJTable JTable
     */
    private void initJTable(JTable currentJTable) {
        currentJTable.putClientProperty("terminateEditOnFocusLost", true);
        DefaultTableModel tableModel = (DefaultTableModel) currentJTable.getModel();
        tableModel.addColumn(" ");
        tableModel.addColumn("KEY");
        tableModel.addColumn("VALUE");
        TableColumn checkColumn = currentJTable.getColumnModel().getColumn(0);
        checkColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        checkColumn.setCellRenderer(currentJTable.getDefaultRenderer(Boolean.class));
        TableColumn keyColumn = currentJTable.getColumnModel().getColumn(1);
        keyColumn.setCellEditor(new MockCellEditor(new JTextField()));
        keyColumn.setCellRenderer(currentJTable.getDefaultRenderer(String.class));
        keyColumn.setResizable(false);
        TableColumn valueColumn = currentJTable.getColumnModel().getColumn(2);
        valueColumn.setCellEditor(new MockCellEditor(new JTextField()));
        valueColumn.setCellRenderer(currentJTable.getDefaultRenderer(String.class));
        valueColumn.setResizable(false);
        checkColumn.setMinWidth(25);
        checkColumn.setMaxWidth(25);
    }

    private void initFormDataJTable(JTable formDataJTable) {
        formDataJTable.putClientProperty("terminateEditOnFocusLost", true);

        // 对tableModel增强
        FormDataTableModel newModel = new FormDataTableModel();
        formDataJTable.setModel(newModel);

        // 设置列数及列名
        newModel.addColumn(" ");
        newModel.addColumn("KEY");
        newModel.addColumn("VALUE");
        newModel.addColumn("");
        newModel.addColumn("");
        // 选择列
        TableColumn checkColumn = formDataJTable.getColumnModel().getColumn(0);
        checkColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        checkColumn.setCellRenderer(formDataJTable.getDefaultRenderer(Boolean.class));
        checkColumn.setMinWidth(25);
        checkColumn.setMaxWidth(25);

        // Key
        TableColumn keyColumn = formDataJTable.getColumnModel().getColumn(1);
        keyColumn.setCellEditor(new MockCellEditor(new JTextField()));
        keyColumn.setCellRenderer(formDataJTable.getDefaultRenderer(String.class));
        keyColumn.setResizable(false);

        // value
        TableColumn valueColumn = formDataJTable.getColumnModel().getColumn(2);
        valueColumn.setCellEditor(new MockCellEditor(new JTextField()));
        valueColumn.setCellRenderer(formDataJTable.getDefaultRenderer(String.class));
        valueColumn.setResizable(false);

        // 是否文件
        TableColumn fileCheckColumn = formDataJTable.getColumnModel().getColumn(3);
        fileCheckColumn.setCellEditor(new JTableCheckBoxEditor(new JCheckBox()));
        fileCheckColumn.setCellRenderer(formDataJTable.getDefaultRenderer(Boolean.class));
        fileCheckColumn.setMinWidth(25);
        fileCheckColumn.setMaxWidth(25);

        // 上传
        TableColumn fileColumn = formDataJTable.getColumnModel().getColumn(4);
        fileColumn.setCellEditor(new JTableButtonEditor());
        fileColumn.setCellRenderer(new JTableButtonRender());
        keyColumn.setResizable(false);

        this.tableModel = newModel;

    }

    private DefaultTableModel tableModel;

    // 设置组件名字及添加到holder中
    private void setName() {
        myToolWindowContent.setName("myToolWindowContent");
        leftRightSplitPane.setName("leftRightSplitPane");
        leftPanel.setName("leftPanel");
        settingFileLocationLabel.setName("settingFileLocationLabel");
        leftTabledPane.setName("leftTabledPane");
        historyPanel.setName("historyPanel");
        groupsPanel.setName("groupsPanel");
        rightPanel.setName("rightPanel");
        httpMethodBox.setName("httpMethodBox");
        urlField.setName("urlField");
        sendButton.setName("sendButton");
        saveFileCheckBox.setName("saveFileCheckBox");
        paramsPane.setName("paramsPane");
        resultTypeComboBox.setName("resultTypeComboBox");
        rightSplitPane.setName("rightSplitPane");
        paramsParentJPanel.setName("paramsParentJPanel");
        responsePanel.setName("responsePanel");
        protocolLabel.setName("protocolLabel");
        paramsPanel.setName("paramsPanel");
        headersPanel.setName("headersPanel");
        bodyPanel.setName("bodyPanel");
        cookiesPanel.setName("cookiesPanel");

        paramsTable.setName("paramsTable");
        headersTable.setName("headersTable");
        noneRadioButton.setName("noneRadioButton");
        formDataRadioButton.setName("formDataRadioButton");
        xWwwFormUrlencodedRadioButton.setName("xWwwFormUrlencodedRadioButton");
        rawRadioButton.setName("rawRadioButton");
        binaryRadioButton.setName("binaryRadioButton");
        rawTypeComboBox.setName("rawTypeComboBox");
        formDataJTable.setName("formDataJTable");
        formDataJScrollPane.setName("formDataJScrollPane");
        urlEncodedJScrollPane.setName("urlEncodedJScrollPane");
        urlEncodedJTable.setName("urlEncodedJTable");
        rawJTextArea.setName("rawJTextArea");
        bodyBinaryButton.setName("bodyBinaryButton");
        bodyParentPanel.setName("bodyParentPanel");
        binaryJPanel.setName("binaryJPanel");
        responseTextArea.setName("responseTextArea");
        historyTree.setName("historyTree");
    }

    public JTabbedPane getParamsPane() {
        return paramsPane;
    }

    public JTable getHeadersTable() {
        return headersTable;
    }

    public JTable getParamsTable() {
        return paramsTable;
    }

    public JTable getFormDataJTable() {
        return formDataJTable;
    }

    public JTable getUrlEncodedJTable() {
        return urlEncodedJTable;
    }

    public JComboBox<String> getResultTypeComboBox() {
        return resultTypeComboBox;
    }

    public JPanel getBodyParentPanel() {
        return bodyParentPanel;
    }

    public JComboBox<String> getHttpMethodBox() {
        return httpMethodBox;
    }

    public JTextField getUrlField() {
        return urlField;
    }

    public JRadioButton getNoneRadioButton() {
        return noneRadioButton;
    }

    public JRadioButton getFormDataRadioButton() {
        return formDataRadioButton;
    }

    public JRadioButton getxWwwFormUrlencodedRadioButton() {
        return xWwwFormUrlencodedRadioButton;
    }

    public JRadioButton getRawRadioButton() {
        return rawRadioButton;
    }

    public JRadioButton getBinaryRadioButton() {
        return binaryRadioButton;
    }

    public JComboBox<String> getRawTypeComboBox() {
        return rawTypeComboBox;
    }

    public JLabel getSettingFileLocationLabel() {
        return settingFileLocationLabel;
    }

    public JScrollPane getFormDataJScrollPane() {
        return formDataJScrollPane;
    }

    public JScrollPane getUrlEncodedJScrollPane() {
        return urlEncodedJScrollPane;
    }

    public JTextArea getRawJTextArea() {
        return rawJTextArea;
    }

    public JPanel getBinaryJPanel() {
        return binaryJPanel;
    }

    public JTextArea getResponseJLabel() {
        return responseTextArea;
    }

    public JTree getHistoryTree() {
        return historyTree;
    }
}
