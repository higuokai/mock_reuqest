package mock.request.http.util;

import mock.request.http.boot.Environment;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * JSON 类型字符 监听, 同时修改值工具
 * @author guokai
 * @version 1.0
 */
public class JsonDocumentListener implements DocumentListener {
    // 是否自动添加
    boolean auto = false;

    private final JTextArea jTextArea;

    public JsonDocumentListener(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println(this);
        // 区分是自动添加还是手动添加
        if (!auto) {
            int insertOffset = e.getOffset();
            int insertLength = e.getLength();
            try {
                Document document = e.getDocument();
                String text = document.getText(insertOffset, insertLength);
                if ("\"".equals(text)) {
                    // 在后面再添加一个
                    Environment.getInstance().getRuntimeChangeTextWorker().insertJTextAreaValue(jTextArea,"\"", insertOffset+1);
                    auto = true;
                } else if ("{".equals(text)) {
                    Environment.getInstance().getRuntimeChangeTextWorker().insertJTextAreaValue(jTextArea,"}", insertOffset+1);
                    auto = true;
                }
            } catch (Exception cause) {
                cause.printStackTrace();
            }
        } else {
            // 是在自动添加之后执行的, 将auto设置为false
            auto = false;
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int length = e.getLength();
        int offset = e.getOffset();
        System.out.println("remove:" + length + ":" +offset);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("属性已被修改");
    }
}
