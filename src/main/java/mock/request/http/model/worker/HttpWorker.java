package mock.request.http.model.worker;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import mock.request.core.constant.Constants;
import mock.request.core.utils.IoUtils;
import mock.request.core.utils.StringUtils;
import mock.request.http.boot.Environment;
import mock.request.http.gui.form.MainWindow;
import mock.request.http.model.ComponentHolder;
import mock.request.http.model.data.PluginData;
import mock.request.http.model.tree.HistoryTreeNode;
import okhttp3.*;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author guokai
 * @version 1.0
 */
public class HttpWorker {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void buildAndSend() {
        // 校验参数是否完整
        MainWindow mainWindow = Environment.getInstance().getComponentHolder().getMainWindow();
        // 返回值区域内容、样式清空
        JTextArea responseJLabel = mainWindow.getResponseJLabel();

        // URL
        StringBuilder urlBuilder = new StringBuilder(mainWindow.getUrlField().getText());
        if (StringUtils.isEmpty(urlBuilder)) {
            // 提示输入URL
            Messages.showMessageDialog("Request URL is empty","Check Error", Messages.getInformationIcon());
            return;
        }
        responseJLabel.setForeground(JBColor.black);
        responseJLabel.setText("请稍后...");

        executorService.execute(() -> {
            // 组装URL
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS).build();
            Request.Builder builder = new Request.Builder();
            String urlParams = getParamsJTableValue(mainWindow);
            if (StringUtils.isNotEmpty(urlParams)) {
                urlBuilder.append("?").append(urlParams);
            }
            builder.url(urlBuilder.toString());

            // 请求头
            this.getHeadersValue(mainWindow, builder);

            RequestBody requestBody = null;
            try {
                requestBody = buildRequestBody(Environment.getInstance().getComponentHolder());
            } catch (RuntimeException e) {
                String message = e.getMessage();
                responseJLabel.setForeground(JBColor.red);
                responseJLabel.setText(message);
            }

            // 获取Http方法
            String httpMethod = mainWindow.getHttpMethodBox().getSelectedItem().toString();
            switch (httpMethod) {
                case "GET":
                    builder.get();
                    break;
                case "POST":
                    builder.post(requestBody);
                    break;
                case "PUT":
                    builder.put(requestBody);
                    break;
                case "DELETE":
                    builder.delete(requestBody);
                    break;
            }
            Request request = builder.build();
            Call call = client.newCall(request);

            Response response;
            try {
                response = call.execute();
                responseParse(response, mainWindow);
                DefaultTreeModel treeModel = (DefaultTreeModel) mainWindow.getHistoryTree().getModel();
                Object root = treeModel.getRoot();
                String nowUrl = urlBuilder.toString();
                int childCount = treeModel.getChildCount(root);
                boolean add = true;
                for(int i=0; i<childCount; i++) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeModel.getChild(root, i);
                    if (Objects.equals(nowUrl, child.getUserObject())) {
                        add = false;
                    }
                }
                if (add) {
                    treeModel.insertNodeInto(new DefaultMutableTreeNode(new HistoryTreeNode(new PluginData(nowUrl))), (MutableTreeNode)root, childCount);
                }
            } catch (Exception e) {
                // 异常,应该输出异常信息
                responseJLabel.setForeground(JBColor.RED);
                responseJLabel.setText(request.toString() + "\r\n" + e.getMessage());
            }
        });
    }

    /**
     * 返回结果处理
     */
    private void responseParse(Response response, MainWindow mainWindow) {
        String resultType = mainWindow.getResultTypeComboBox().getSelectedItem().toString().toLowerCase();
        String text = "";
        if ("json".equals(resultType)) {
            try {
                text = response.body().string();
                text = StringUtils.formatJsonString(text);
            } catch (Exception e) {
                // 不是json格式字符串
                e.printStackTrace();
            }
            mainWindow.getResponseJLabel().setText(text);
        } else if ("file".equals(resultType)) {
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                inputStream = response.body().byteStream();
                // 下载文件到桌面?
                outputStream = new FileOutputStream(new File("C:\\Users\\e\\Desktop\\" + new SimpleDateFormat("yyyyMMdd hh:mm:ss")));
                IOUtils.copy(inputStream, outputStream);
            } catch (Exception cause) {
                cause.printStackTrace();
            } finally {
                IoUtils.close(inputStream);
                IoUtils.close(outputStream);
            }

        } else {
            try {
                text = response.body().string();
            } catch (Exception e) {
                // nothing
            }
            mainWindow.getResponseJLabel().setText(text);
        }
    }

    private String getParamsJTableValue(MainWindow mainWindow) {
        JTable paramsTable = mainWindow.getParamsTable();
        Map<String, String> params = new LinkedHashMap<>();

        if (!getJTableValue(params, paramsTable, "Query params")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        params.forEach((key, value) -> {
            sb.append(key).append("=").append(value).append("&");
        });
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }

    private void getHeadersValue(MainWindow mainWindow, Request.Builder builder) {
        JTable headersTable = mainWindow.getHeadersTable();
        Map<String, String> params = new HashMap<>();
        getJTableValue(params, headersTable, "Headers key/value");
        // 设置请求头
        params.forEach(builder::addHeader);
    }

    private boolean getJTableValue(Map<String, String> params, JTable jTable, String tableName) {
        if (jTable == null) {
            return false;
        }
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        for (int i=0; i<model.getRowCount(); i++) {
            // 查看第一列选中了哪些
            Object valueAt = model.getValueAt(i, 0);
            if (Objects.equals(valueAt, Boolean.TRUE)) {
                // 选中, 获取第二列、第三列值
                Object key = model.getValueAt(i, 1);
                Object value = model.getValueAt(i, 2);
                params.put(StringUtils.isEmpty(key)?"":key.toString(), StringUtils.isEmpty(value)?"":value.toString());
            }
        }
        return true;
    }

    private RequestBody buildRequestBody(ComponentHolder componentHolder) {
        // none, 忽略
        JComponent currentBodyComponent = componentHolder.getCurrentBodyComponent();
        String bodyComponentName = currentBodyComponent.getName();

        RequestBody requestBody = null;
        switch (bodyComponentName) {
            case Constants.BODY_TYPE_NONE:
                // do nothing
                break;
            case Constants.BODY_TYPE_FORM:
                // 表单, 区分类型
                requestBody = buildFormData(componentHolder.getMainWindow().getFormDataJTable());
                break;
            case Constants.BODY_TYPE_URLENCODED:
                requestBody = buildEncoded(componentHolder.getMainWindow().getUrlEncodedJTable());
                break;
            case Constants.BODY_TYPE_BINARY:
                break;
            case Constants.BODY_TYPE_RAW:
                requestBody = buildRawBody(componentHolder.getMainWindow());
                break;
            default:
                break;
        }
        return requestBody;
    }

    private RequestBody buildBinary(MainWindow mainWindow) {
        return null;
    }

    private RequestBody buildRawBody(MainWindow mainWindow) {
        JComboBox<String> rawTypeComboBox = mainWindow.getRawTypeComboBox();
        String rawType = rawTypeComboBox.getSelectedItem().toString().toLowerCase();

        MediaType mediaType = MediaType.parse("application/"+rawType+"; charset=utf-8");;
        String text = mainWindow.getRawJTextArea().getText();
        return FormBody.create(mediaType, text);
    }

    /**
     *      check  key  value  file
     *        √     xx    yy      √
     * @param jTable form表单 jTable
     */
    private RequestBody buildFormData(JTable jTable) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();

        Map<String, String> keyValueMap = new LinkedHashMap<>();
        Map<String, String> keyFileMap = new LinkedHashMap<>();

        for (int i=0; i<model.getRowCount(); i++) {
            // 判断有没有选中
            Object checked = model.getValueAt(i, 0);
            if (Objects.equals(true, checked)) {
                // 判断是不是文件
                if (Objects.equals(true, model.getValueAt(i, 3))) {
                    keyFileMap.put(model.getValueAt(i, 1).toString(), model.getValueAt(i, 2).toString());
                } else {
                    keyValueMap.put(model.getValueAt(i, 1).toString(), model.getValueAt(i, 2).toString());
                }
            }
        }

        // 组装请求体
        if (keyFileMap.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            keyValueMap.forEach(builder::add);
            return builder.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE);
            keyValueMap.forEach(builder::addFormDataPart);
            keyFileMap.forEach((k, v) -> {
                File file = new File(v);
                if (!file.exists()) {
                    throw new RuntimeException("文件不能为空");
                }
                builder.addFormDataPart(k, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            });
            return builder.build();
        }
    }

    public RequestBody buildEncoded(JTable jTable) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        FormBody.Builder builder = new FormBody.Builder();
        for (int i=0; i<model.getRowCount(); i++) {
            // 判断有没有选中
            Object checked = model.getValueAt(i, 0);
            if (Objects.equals(true, checked)) {
                builder.addEncoded(model.getValueAt(i, 1).toString(), model.getValueAt(i, 2).toString());
            }
        }
        return builder.build();
    }

}
