package mock.request.http.model.tree;

import mock.request.http.model.data.PluginData;

import java.io.Serializable;

/**
 * @author guokai
 * @version 1.0
 */
public class HistoryTreeNode implements Serializable {

    private String path;

    private PluginData object;

    public HistoryTreeNode(PluginData object) {
        this.object = object;
        this.path = object.getUrl();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    public PluginData getObject() {
        return object;
    }

    public void setObject(PluginData object) {
        this.object = object;
    }
}
