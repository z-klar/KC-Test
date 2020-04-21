import JsonSchemas.ClientRepresentation;
import JsonSchemas.ClientRepresentation;
import JsonSchemas.CredentialRepresentation;
import JsonSchemas.RoleRepresentation;
import JsonSchemas.UserRepresentation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*************************************************************************************
 ************************************************************************************/
public class frmMain2 implements ActionListener {
    private JPanel panel2;
    private JTabbedPane tabbedPane1;
    private JButton btnGetAccessToken2;
    private JTextField txMainResultCode2;
    private JTextArea txaAccessToken2;
    private JTextField txKcBaseUrl;
    private JTextField txAdminTokenUri;
    private JList lbLog;
    private JButton btnClearLog;
    private JTextField txMainResultMessage;
    private JTextArea txaAccessToken;
    private JTextArea txaRefreshToken;
    private JButton btnCallApiWithAccess;
    private JButton btnReastCallWithRefresh;
    private JTextField txRestCallUrl;
    private JList lbUsers;
    private JButton btnGetUsers;
    private JTextArea txaUserRoles;
    private JButton getRolesButton;
    private JButton testUserButton;
    private JTextArea txaRealmRoles;
    private JButton btnSetMemberRole;
    private JButton brnUpdatePassword;
    private JButton btnDisableUser;
    private JButton btnEnableUser;
    private JButton btnLoginUser;
    private JButton btnGetActiveSessions;
    private JButton btnGetClients;
    private JButton btnQuitSession;
    private JTextField txSessionId;
    private JButton btnMap01;
    private JTextField txUserName;
    private JTextField txPassword;
    private JButton btnGetRealms;
    private JList lbRealms;

    private DefaultListModel<String> dlmLog = new DefaultListModel<>();

    private KeycloakTools kcTools;

    private static final Logger log = LoggerFactory.getLogger(frmMain2.class);
    private String accessToken, refreshToken;
    private DefaultListModel<String> dlmUsers = new DefaultListModel<>();
    private DefaultListModel<String> dlmRealms = new DefaultListModel<>();

    private ArrayList<UserRepresentation> alUsers = new ArrayList<>();
    private ArrayList<RoleRepresentation> alRoles = new ArrayList<>();
    private ArrayList<ClientRepresentation> alClients = new ArrayList<>();

    private int selRowUser;
    private JPopupMenu popImages;
    private JMenuItem mnuPpImgRemove;


    /*#############################################################################

    #############################################################################*/
    public frmMain2() {
        btnClearLog.addActionListener(e -> dlmLog.clear());

        OurInit();

        btnGetAccessToken2.addActionListener(e -> GetAdminToken());
        btnCallApiWithAccess.addActionListener(e -> RestCallWithAccessToken());
        btnGetUsers.addActionListener(e -> GetUsers());
        testUserButton.addActionListener(e -> TestNewUser());
        getRolesButton.addActionListener(e -> ReadRoles());
        btnSetMemberRole.addActionListener(e -> SetMemberRole());
        brnUpdatePassword.addActionListener(e -> UpdatePassword());
        btnDisableUser.addActionListener(e -> EnableUser(false));
        btnEnableUser.addActionListener(e -> EnableUser(true));
        btnLoginUser.addActionListener(e -> LoginUser());
        btnGetActiveSessions.addActionListener(e -> GetActiveSessions());
        btnGetClients.addActionListener(e -> GetClients());
        btnQuitSession.addActionListener(e -> QuitSession());
        btnMap01.addActionListener(e -> DataTools.map01());

        CreatePopups();
        lbUsers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                UsersMouseHandler(e);
            }
        });
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void UsersMouseHandler(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON3) {
            selRowUser = lbUsers.getSelectedIndex();
            popImages.show(e.getComponent(), e.getX(), e.getY());
        }

    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void CreatePopups() {
        popImages = new JPopupMenu("pop_users");
        mnuPpImgRemove = new JMenuItem("Get Roles");
        mnuPpImgRemove.addActionListener(this);
        popImages.add(mnuPpImgRemove);
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        String mnuLabel = ((JPopupMenu) source.getComponent().getParent()).getLabel();
        log.info("Menu label: " + mnuLabel);

        switch (mnuLabel) {
            case "pop_users":
                if (source.getText().compareTo("Get Roles") == 0) {
                    String userName = lbUsers.getSelectedValue().toString();
                    String id = DataTools.getUserIdByName(alUsers, userName);
                    //JOptionPane.showMessageDialog(null, "User ID: " + id);
                    GetUserRoles(id);
                }
                break;

        }

    }


    /*===========================================================================

     ==========================================================================*/
    private void OurInit() {

        kcTools = new KeycloakTools(txKcBaseUrl.getText(), txAdminTokenUri.getText());

        lbLog.setModel(dlmLog);
        lbUsers.setModel(dlmUsers);

    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void QuitSession() {
        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = String.format("admin/realms/rtl/sessions/%s", txSessionId.getText());
            res = kcTools.sendApiCallDelete(url, accessToken);
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");
        }
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void GetClients() {
        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = "admin/realms/rtl/clients";
            res = kcTools.sendApiCallGet(url, accessToken);
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

            if (res.ResCode < 300) {

                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonElement json = gson.fromJson(res.ResMsg, JsonElement.class);
                    String jsonInString = gson.toJson(json);
                    txaRealmRoles.setText(jsonInString);

                    alUsers.clear();
                    Type clientListType = new TypeToken<ArrayList<ClientRepresentation>>() {
                    }.getType();
                    alClients = gson.fromJson(res.ResMsg, clientListType);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void GetActiveSessions() {

        String clientId = "";
        for (int i = 0; i < alClients.size(); i++) {
            ClientRepresentation client = alClients.get(i);
            if (client.getClientId().compareTo("rtl-app") == 0) {
                clientId = client.getId();
                break;
            }
        }
        if (clientId.length() == 0) {
            JOptionPane.showMessageDialog(null, "Client RTL-APP not found !!!");
            return;
        }

        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = String.format("admin/realms/rtl/clients/%s/user-sessions", clientId);
            res = kcTools.sendApiCallGet(url, accessToken);
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

            if (res.ResCode < 300) {
                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonElement json = gson.fromJson(res.ResMsg, JsonElement.class);
                    String jsonInString = gson.toJson(json);
                    txaRealmRoles.setText(jsonInString);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void EnableUser(boolean en) {
        String userId = "";
        int userIndex = -1;
        for (int i = 0; i < alUsers.size(); i++) {
            UserRepresentation user = alUsers.get(i);
            if (user.getUsername().compareTo("zdenda-klar") == 0) {
                userId = user.getId();
                userIndex = i;
                break;
            }
        }
        if (userId.length() == 0) {
            JOptionPane.showMessageDialog(null, "User NOT found !");
            return;
        }

        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {

            UserRepresentation user = alUsers.get(userIndex);
            // update ENABLED
            user.setEnabled(en);

            String url = String.format("admin/realms/rtl/users/%s", userId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Java objects to String
            String jsonData = gson.toJson(user);

            res = kcTools.sendApiCallPutBody(url, accessToken, jsonData, "application/json");
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");
        }
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private HttpQueryResult LoginUser() {
        HttpQueryResult res = kcTools.getLoginToken("rtl-web-app", txUserName.getText(), txPassword.getText());
        txMainResultCode2.setText(String.format("%d", res.ResCode));
        txMainResultMessage.setText(res.ResText);

        //txaAccessToken2.setText(res.ResMsg);
        if (res.ResCode < 300) {
            Object obj = null;
            JSONObject jsobject;

            try {
                JSONParser parser = new JSONParser();
                obj = parser.parse(res.ResMsg);
                txaAccessToken2.setText(JsonTools.ConvertJsonToString(obj));
                jsobject = (JSONObject) obj;

                accessToken = (String) jsobject.get("access_token");
                txaAccessToken.setText(accessToken);
                txaRealmRoles.setText(accessToken);
                refreshToken = (String) jsobject.get("refresh_token");
                txaRefreshToken.setText(refreshToken);
                return (res);
            } catch (Exception ex) {
                return (null);
            }
        } else {
            txaAccessToken.setText(String.format("Error:  RESPONSE = %d", res.ResCode));
            return (null);
        }

    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void UpdatePassword() {

        String userId = "";
        int userIndex = -1;
        for (int i = 0; i < alUsers.size(); i++) {
            UserRepresentation user = alUsers.get(i);
            if (user.getUsername().compareTo("zdenda-klar") == 0) {
                userId = user.getId();
                userIndex = i;
                break;
            }
        }
        if (userId.length() == 0) {
            JOptionPane.showMessageDialog(null, "User NOT found !");
            return;
        }

        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {

            UserRepresentation user = alUsers.get(userIndex);
            // update credentials
            CredentialRepresentation[] cred = new CredentialRepresentation[1];
            cred[0] = new CredentialRepresentation("password", "123456", false);
            user.setCredentials(cred);

            String url = String.format("admin/realms/rtl/users/%s", userId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Java objects to String
            String jsonData = gson.toJson(user);

            res = kcTools.sendApiCallPutBody(url, accessToken, jsonData, "application/json");
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

        }


    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void SetMemberRole() {

        String userId = "", roleId = "", roleName = "";

        for (int i = 0; i < alUsers.size(); i++) {
            UserRepresentation user = alUsers.get(i);
            if (user.getUsername().compareTo("zdenda-klar") == 0) {
                userId = user.getId();
                break;
            }
        }
        if (userId.length() == 0) {
            JOptionPane.showMessageDialog(null, "User NOT found !");
            return;
        }

        for (int i = 0; i < alRoles.size(); i++) {
            RoleRepresentation role = alRoles.get(i);
            if (role.getName().compareTo("Member") == 0) {
                roleId = role.getId();
                roleName = "Member";
                break;
            }
        }
        if (roleId.length() == 0) {
            JOptionPane.showMessageDialog(null, "Role NOT found !");
            return;
        }

        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = String.format("admin/realms/rtl/users/%s/role-mappings/realm", userId);
            String jsonData = String.format("[  { \"id\": \"%s\",  \"name\": \"%s\" } ]", roleId, roleName);
            res = kcTools.sendApiCallPostBody(url, accessToken, jsonData, "application/json");
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

        }


    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void ReadRoles() {
        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = "admin/realms/rtl/roles";
            res = kcTools.sendApiCallGet(url, accessToken);
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

            if (res.ResCode < 300) {
                try {
                    Gson gson = new Gson();
                    JSONParser parser = new JSONParser();

                    alRoles.clear();
                    Type roleListType = new TypeToken<ArrayList<RoleRepresentation>>() {
                    }.getType();
                    alRoles = gson.fromJson(res.ResMsg, roleListType);

                    String s = "";
                    for (RoleRepresentation r : alRoles) {
                        s += r.toString() + "\n";
                    }
                    txaRealmRoles.setText(s);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void TestNewUser() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        UserRepresentation user = new UserRepresentation("zdenda-klar");
        user.setEnabled(true);
        user.setEmail("zdenek.klar@gmail.com");
        user.setFirstName("Zdenek");
        user.setLastName("Klar");

        CredentialRepresentation[] cred = new CredentialRepresentation[1];
        cred[0] = new CredentialRepresentation("password", "123", false);
        user.setCredentials(cred);

        // Java objects to String
        String json = gson.toJson(user);
        Loguj(json);

        CreateNewUser(json);
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void CreateNewUser(String jsonData) {

        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = "admin/realms/rtl/users";
            res = kcTools.sendApiCallPostBody(url, accessToken, jsonData, "application/json");
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

        }
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void GetUserRoles(String userId) {
        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = String.format("admin/realms/rtl/users/%s/role-mappings/realm", userId);
            res = kcTools.sendApiCallGet(url, accessToken);
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

            /*
            if (res.ResCode < 300) {
                Object obj = null, obj2, obj1;
                String sResult = "", classname, out = "";
                JSONObject jsobject;

                try {
                    JSONParser parser = new JSONParser();
                    obj = parser.parse(res.ResMsg);
                    txaUserRoles.setText(JsonTools.ConvertJsonToString(obj));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
             */

            if (res.ResCode < 300) {

                try {
                    String sRoles = "";

                    Gson gson = new Gson();
                    Type userListType = new TypeToken<ArrayList<RoleRepresentation>>() {
                    }.getType();
                    ArrayList<RoleRepresentation> roleArray = gson.fromJson(res.ResMsg, userListType);
                    for (RoleRepresentation r : roleArray) {
                        sRoles += r.toString() + "\n";
                    }
                    txaUserRoles.setText(sRoles);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void GetUsers() {
        HttpQueryResult res = GetAdminToken();

        if (res.ResCode < 300) {
            String url = "admin/realms/rtl/users";
            res = kcTools.sendApiCallGet(url, accessToken);
            txMainResultCode2.setText(String.format("%d", res.ResCode));
            txMainResultMessage.setText(res.ResText);
            txaAccessToken2.setText("");

            if (res.ResCode < 300) {
                Object obj = null, obj2, obj1;
                String sResult = "", classname, out = "";
                JSONObject jsobject;

                try {
                    Gson gson = new Gson();
                    JSONParser parser = new JSONParser();
                    //KeycloakUsers users = gson.fromJson(res.ResMsg, KeycloakUsers.class);

                    dlmUsers.clear();
                    alUsers.clear();
                    Type userListType = new TypeToken<ArrayList<UserRepresentation>>() {
                    }.getType();
                    //ArrayList<UserRepresentation> userArray = gson.fromJson(res.ResMsg, userListType);
                    alUsers = gson.fromJson(res.ResMsg, userListType);
                    for (UserRepresentation u : alUsers) {
                        //log.info(u.toString());
                        //alUsers.add(u.getUsername());
                        dlmUsers.addElement(u.getUsername());
                    }

                    /*
                    obj = parser.parse(res.ResMsg);
                    txaAccessToken2.setText(JsonTools.ConvertJsonToString(obj));

                    dlmUsers.clear();
                    alUsers.clear();
                    JSONArray jsa = (JSONArray) obj;
                    for (Object userObj : jsa) {
                        JSONObject user = (JSONObject) userObj;
                        alUsers.add(user);
                        dlmUsers.addElement((String) user.get("username"));
                    }
                     */

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void RestCallWithAccessToken() {
        HttpQueryResult res = kcTools.sendApiCallGet(txRestCallUrl.getText(), accessToken);
        txMainResultCode2.setText(String.format("%d", res.ResCode));
        txMainResultMessage.setText(res.ResText);
        txaAccessToken2.setText("");

        if (res.ResCode < 300) {
            Object obj = null, obj2, obj1;
            String sResult = "", classname, out = "";
            JSONObject jsobject;

            try {
                JSONParser parser = new JSONParser();
                obj = parser.parse(res.ResMsg);
                txaAccessToken2.setText(JsonTools.ConvertJsonToString(obj));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private HttpQueryResult GetAdminToken() {
        HttpQueryResult res = kcTools.getAdminToken();
        txMainResultCode2.setText(String.format("%d", res.ResCode));
        txMainResultMessage.setText(res.ResText);

        //txaAccessToken2.setText(res.ResMsg);
        if (res.ResCode < 300) {
            Object obj = null, obj2, obj1;
            String sResult = "", classname, out = "";
            JSONObject jsobject;

            try {
                JSONParser parser = new JSONParser();
                obj = parser.parse(res.ResMsg);
                txaAccessToken2.setText(JsonTools.ConvertJsonToString(obj));
                jsobject = (JSONObject) obj;

                accessToken = (String) jsobject.get("access_token");
                txaAccessToken.setText(accessToken);
                refreshToken = (String) jsobject.get("refresh_token");
                txaRefreshToken.setText(refreshToken);
                return (res);
            } catch (Exception ex) {
                return (null);
            }
        } else {
            return (null);
        }
    }

    /*-----------------------------------------------------------------------------

    ------------------------------------------------------------------------------*/
    private void Loguj(String msg) {

        String[] result = msg.split("\n");
        for (int x = 0; x < result.length; x++)
            dlmLog.addElement(result[x]);

    }

    /*--------------------------------------------------

    -----------------------------------------------------------------------------*/
    public static void main(String[] args) {
        JFrame frame = new JFrame("KeyCloak");
        frame.setContentPane(new frmMain2().panel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 800);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1 = new JTabbedPane();
        panel2.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Main", panel1);
        btnGetAccessToken2 = new JButton();
        btnGetAccessToken2.setText("Get Access Token");
        panel1.add(btnGetAccessToken2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("HTTP Result:");
        panel1.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txMainResultCode2 = new JTextField();
        txMainResultCode2.setBackground(new Color(-16777216));
        Font txMainResultCode2Font = this.$$$getFont$$$("Courier New", -1, 14, txMainResultCode2.getFont());
        if (txMainResultCode2Font != null) txMainResultCode2.setFont(txMainResultCode2Font);
        txMainResultCode2.setForeground(new Color(-10421442));
        panel1.add(txMainResultCode2, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("HTTP Message:");
        panel1.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCallApiWithAccess = new JButton();
        btnCallApiWithAccess.setText("REST Call W/ AccessToken");
        panel1.add(btnCallApiWithAccess, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnReastCallWithRefresh = new JButton();
        btnReastCallWithRefresh.setText("REST Call W/ RefreshToken");
        panel1.add(btnReastCallWithRefresh, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("REST Call URL:");
        panel1.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txRestCallUrl = new JTextField();
        txRestCallUrl.setBackground(new Color(-16777216));
        Font txRestCallUrlFont = this.$$$getFont$$$("Courier New", -1, 14, txRestCallUrl.getFont());
        if (txRestCallUrlFont != null) txRestCallUrl.setFont(txRestCallUrlFont);
        txRestCallUrl.setForeground(new Color(-10421442));
        txRestCallUrl.setText("admin/realms/rtl/users");
        panel1.add(txRestCallUrl, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txaAccessToken2 = new JTextArea();
        txaAccessToken2.setBackground(new Color(-15329253));
        txaAccessToken2.setEditable(false);
        Font txaAccessToken2Font = this.$$$getFont$$$("Courier New", Font.BOLD, 14, txaAccessToken2.getFont());
        if (txaAccessToken2Font != null) txaAccessToken2.setFont(txaAccessToken2Font);
        txaAccessToken2.setForeground(new Color(-11282636));
        txaAccessToken2.setLineWrap(true);
        txaAccessToken2.setWrapStyleWord(false);
        scrollPane1.setViewportView(txaAccessToken2);
        txMainResultMessage = new JTextField();
        txMainResultMessage.setBackground(new Color(-16777216));
        Font txMainResultMessageFont = this.$$$getFont$$$("Courier New", -1, 14, txMainResultMessage.getFont());
        if (txMainResultMessageFont != null) txMainResultMessage.setFont(txMainResultMessageFont);
        txMainResultMessage.setForeground(new Color(-10421442));
        panel1.add(txMainResultMessage, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Logger", panel3);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lbLog = new JList();
        lbLog.setBackground(new Color(-16777216));
        Font lbLogFont = this.$$$getFont$$$("Courier New", Font.BOLD, 14, lbLog.getFont());
        if (lbLogFont != null) lbLog.setFont(lbLogFont);
        lbLog.setForeground(new Color(-11282636));
        scrollPane2.setViewportView(lbLog);
        btnClearLog = new JButton();
        btnClearLog.setText("Clear");
        panel3.add(btnClearLog, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Configuration", panel4);
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-16777216));
        label4.setText("KeyCloak Base URL:");
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-16777216));
        label5.setText("Admin Token URI:");
        panel4.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(114, 16), null, 0, false));
        txKcBaseUrl = new JTextField();
        txKcBaseUrl.setText("http://localhost:8080/auth/");
        panel4.add(txKcBaseUrl, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txAdminTokenUri = new JTextField();
        txAdminTokenUri.setText("realms/master/protocol/openid-connect/token");
        panel4.add(txAdminTokenUri, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Tokens", panel5);
        final JLabel label6 = new JLabel();
        label6.setText("Access Token:");
        panel5.add(label6, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Refresh Token:");
        panel5.add(label7, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel5.add(scrollPane4, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Users:");
        panel5.add(label8, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Roles:");
        panel5.add(label9, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        panel5.add(scrollPane5, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txaAccessToken = new JTextArea();
        txaAccessToken.setBackground(new Color(-16777216));
        Font txaAccessTokenFont = this.$$$getFont$$$("Courier New", Font.BOLD, 14, txaAccessToken.getFont());
        if (txaAccessTokenFont != null) txaAccessToken.setFont(txaAccessTokenFont);
        txaAccessToken.setForeground(new Color(-787676));
        txaAccessToken.setLineWrap(true);
        scrollPane5.setViewportView(txaAccessToken);
        final JScrollPane scrollPane6 = new JScrollPane();
        panel5.add(scrollPane6, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txaRefreshToken = new JTextArea();
        txaRefreshToken.setBackground(new Color(-16777216));
        Font txaRefreshTokenFont = this.$$$getFont$$$("Courier New", Font.BOLD, 14, txaRefreshToken.getFont());
        if (txaRefreshTokenFont != null) txaRefreshToken.setFont(txaRefreshTokenFont);
        txaRefreshToken.setForeground(new Color(-787676));
        txaRefreshToken.setLineWrap(true);
        scrollPane6.setViewportView(txaRefreshToken);
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Main API Tests", panel6);
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setDividerLocation(33);
        splitPane1.setDividerSize(4);
        splitPane1.setOrientation(0);
        panel6.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JSplitPane splitPane2 = new JSplitPane();
        splitPane2.setDividerSize(3);
        splitPane2.setOrientation(0);
        splitPane1.setRightComponent(splitPane2);
        final JScrollPane scrollPane7 = new JScrollPane();
        splitPane2.setLeftComponent(scrollPane7);
        final JScrollPane scrollPane8 = new JScrollPane();
        scrollPane7.setViewportView(scrollPane8);
        txaRealmRoles = new JTextArea();
        txaRealmRoles.setBackground(new Color(-16777216));
        Font txaRealmRolesFont = this.$$$getFont$$$("Courier New", Font.BOLD, 14, txaRealmRoles.getFont());
        if (txaRealmRolesFont != null) txaRealmRoles.setFont(txaRealmRolesFont);
        txaRealmRoles.setForeground(new Color(-10421442));
        txaRealmRoles.setLineWrap(true);
        txaRealmRoles.setText("");
        scrollPane8.setViewportView(txaRealmRoles);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane2.setRightComponent(panel7);
        final JSplitPane splitPane3 = new JSplitPane();
        splitPane3.setDividerLocation(500);
        splitPane3.setDividerSize(3);
        panel7.add(splitPane3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        splitPane3.setLeftComponent(panel8);
        final JLabel label10 = new JLabel();
        label10.setText("   Users:");
        panel8.add(label10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel8.add(spacer5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnGetUsers = new JButton();
        btnGetUsers.setText("Get Users");
        panel8.add(btnGetUsers, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane9 = new JScrollPane();
        panel8.add(scrollPane9, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lbUsers = new JList();
        lbUsers.setBackground(new Color(-16777216));
        Font lbUsersFont = this.$$$getFont$$$("Courier New", Font.BOLD, 14, lbUsers.getFont());
        if (lbUsersFont != null) lbUsers.setFont(lbUsersFont);
        lbUsers.setForeground(new Color(-9911809));
        scrollPane9.setViewportView(lbUsers);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(2, 7, new Insets(0, 0, 0, 0), -1, -1));
        splitPane3.setRightComponent(panel9);
        final JLabel label11 = new JLabel();
        label11.setAlignmentY(0.1f);
        label11.setText("  Roles:");
        panel9.add(label11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel9.add(spacer6, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane10 = new JScrollPane();
        panel9.add(scrollPane10, new GridConstraints(1, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txaUserRoles = new JTextArea();
        txaUserRoles.setBackground(new Color(-16777216));
        Font txaUserRolesFont = this.$$$getFont$$$("Courier New", Font.BOLD, 14, txaUserRoles.getFont());
        if (txaUserRolesFont != null) txaUserRoles.setFont(txaUserRolesFont);
        txaUserRoles.setForeground(new Color(-10421442));
        scrollPane10.setViewportView(txaUserRoles);
        testUserButton = new JButton();
        testUserButton.setText("New User");
        panel9.add(testUserButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSetMemberRole = new JButton();
        btnSetMemberRole.setText("Set MEMBER Role");
        panel9.add(btnSetMemberRole, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        brnUpdatePassword = new JButton();
        brnUpdatePassword.setText("Update Password");
        panel9.add(brnUpdatePassword, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDisableUser = new JButton();
        btnDisableUser.setText("Disable User");
        panel9.add(btnDisableUser, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEnableUser = new JButton();
        btnEnableUser.setText("Enable User");
        panel9.add(btnEnableUser, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 13, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(panel10);
        getRolesButton = new JButton();
        getRolesButton.setText("Get Roles");
        panel10.add(getRolesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(78, 18), null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel10.add(spacer7, new GridConstraints(0, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Roles");
        panel10.add(label12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(28, 18), null, 0, false));
        btnLoginUser = new JButton();
        btnLoginUser.setText("Login User");
        panel10.add(btnLoginUser, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(78, 18), null, 0, false));
        btnGetActiveSessions = new JButton();
        btnGetActiveSessions.setText("Get Active Sessions");
        panel10.add(btnGetActiveSessions, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(78, 18), null, 0, false));
        btnGetClients = new JButton();
        btnGetClients.setText("Get Clients");
        panel10.add(btnGetClients, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(78, 18), null, 0, false));
        btnQuitSession = new JButton();
        btnQuitSession.setText("Quit Sessions");
        panel10.add(btnQuitSession, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(78, 18), null, 0, false));
        txSessionId = new JTextField();
        txSessionId.setBackground(new Color(-16777216));
        Font txSessionIdFont = this.$$$getFont$$$("Courier New", -1, 14, txSessionId.getFont());
        if (txSessionIdFont != null) txSessionId.setFont(txSessionIdFont);
        txSessionId.setForeground(new Color(-10421442));
        panel10.add(txSessionId, new GridConstraints(0, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Session ID:");
        panel10.add(label13, new GridConstraints(0, 10, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Usr:");
        panel10.add(label14, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txUserName = new JTextField();
        panel10.add(txUserName, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("PWD:");
        panel10.add(label15, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txPassword = new JTextField();
        panel10.add(txPassword, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Mapper Tests", panel11);
        btnMap01 = new JButton();
        btnMap01.setText("Map SRC01 to DST01");
        panel11.add(btnMap01, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel11.add(spacer8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel11.add(spacer9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel2;
    }

}
