package mock.request.http.util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;

/**
 * JSON 类型字符 监听, 同时修改值工具
 * @author guokai
 * @version 1.0
 */
public class JsonDocumentListener implements DocumentListener {

    private final JTextArea jTextArea;

    public JsonDocumentListener(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("属性已被修改");
    }
}
