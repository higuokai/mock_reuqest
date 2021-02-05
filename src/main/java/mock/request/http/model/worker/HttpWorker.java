package mock.request.http.model.worker;

import com.intellij.openapi.ui.Messages;
import mock.request.core.constant.Constants;
import mock.request.core.utils.StringUtils;
import mock.request.http.boot.Environment;
import mock.request.http.gui.form.MainWindow;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author guokai
 * @version 1.0
 */
public class HttpWorker {

    public void buildAndSend() {
        // 校验参数是否完整
        MainWindow mainWindow = Environment.getInstance().getComponentHolder().getMainWindow();
        String url = mainWindow.getUrlField().getText();
        if (StringUtils.isEmpty(url)) {
            // 提示输入URL
            Messages.showMessageDialog("Request URL is empty","Check Error", Messages.getInformationIcon());
            return;
        }
        // 获取Http方法
        String httpMethod = mainWindow.getHttpMethodBox().getSelectedItem().toString();

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        String urlParams = getParamsJTableValue(mainWindow);
        if (StringUtils.isNotEmpty(urlParams)) {
            url =  url + "?" + urlParams;
        }
        builder.url(url);

        // 请求头
        this.getHeadersValue(mainWindow, builder);

        // 请求体
        if ("GET".equals(httpMethod)) {
            try {
                Response response = client.newCall(builder.build()).execute();
                String string = response.body().string();
                int code = response.code();
                if (code != 200) {
                    mainWindow.getResponseJLabel().setText(url + "--" + string);
                    mainWindow.getResponseJLabel().setForeground(Color.red);
                } else {
                    mainWindow.getResponseJLabel().setText(string);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("POST".equals(httpMethod)) {
            System.out.println("post请求");
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

}
