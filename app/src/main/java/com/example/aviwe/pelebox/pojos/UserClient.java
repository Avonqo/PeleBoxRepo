package com.example.aviwe.pelebox.pojos;


public class UserClient
{
    private int userclientId;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private int roleId;
    private String userEmail;
    private String Token;
    private String Timeout;

    public UserClient() {

    }

    public UserClient(int userclientId, String userFirstName, String userLastName, String userPassword, int roleId, String userEmail, String token, String timeout) {
        this.userclientId = userclientId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.roleId = roleId;
        this.userEmail = userEmail;
        Token = token;
        Timeout = timeout;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getTimeout() {
        return Timeout;
    }

    public void setTimeout(String timeout) {
        Timeout = timeout;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserclientId() {
        return userclientId;
    }

    public void setUserclientId(int userclientId) {
        this.userclientId = userclientId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
