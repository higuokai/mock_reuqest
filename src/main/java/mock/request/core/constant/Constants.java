package mock.request.core.constant;

import mock.request.core.utils.StringUtils;

/**
 * @author guokai
 * @version 1.0
 */
@SuppressWarnings("unused")
public interface Constants {

    /**
     * 插件ID
     */
    String PLUGIN_ID = "mock.postman";

    String TOOL_WINDOW_ID = "Mock Postman";

    String PROPERTIES_KEY_SETTING_FILE_LOCATION = StringUtils.join(PLUGIN_ID, ".", "setting.file.location.key");

    String PROPERTIES_KEY_URL = StringUtils.join(PLUGIN_ID, ".", "url");

    String PROPERTIES_KEY_HTTP_METHOD = StringUtils.join(PLUGIN_ID, ".", "http.method");

    String PROPERTIES_KEY_SAVE_FILE = PLUGIN_ID + ".save.file";

    String PROPERTIES_KEY_PARAM_PANEL = PLUGIN_ID + ".param.panel.index";

    String PROPERTIES_KEY_BODY_RADIO = PLUGIN_ID + ".body.radio";

    String PROPERTIES_KEY_RAW_TYPE = PLUGIN_ID + ".raw.type";

    String PROPERTIES_KEY_BODY_PANEL = PLUGIN_ID + ".body_panel.index";

    String PROPERTIES_KEY_BODY_VISIBLE = Constants.PLUGIN_ID + ".body.visible.key";

    String MOCK_POSTMAN_ACTION = "Mock postman";

    String MOCK_POSTMAN_ACTION_TEXT = "Mock Postman";


    //   body 类型------------------------------------
    String BODY_TYPE_FORM = "formDataJScrollPane";

    String BODY_TYPE_URLENCODED = "urlEncodedJScrollPane";

    String BODY_TYPE_RAW = "rawJTextArea";

    String BODY_TYPE_BINARY = "bodyBinaryButton";

    String BODY_TYPE_NONE = "bodyTypeNone";
    // ------------------------------------

    //   参数JPanel 类型------------------------------------
    String PARAMS_J_PANEL_PARAMS = "paramsPanel";

    String PARAMS_J_PANEL_HEADERS = "headersPanel";

    String PARAMS_J_PANEL_BODY = "bodyPanel";

    String PARAMS_J_PANEL_COOKIES = "cookiesPanel";


    String PROTOCOL_HTTP = "http://";

}
