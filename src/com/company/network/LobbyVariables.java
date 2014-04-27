package com.company.network;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 27.04.14
 * Time: 09:45
 */
public class LobbyVariables {
    public boolean isSearching = true;
    public int seconds = 0;
    public int connectedClients = 0;
    public boolean error = false;
    public boolean isReady = false;
    public String errorMsg = "";

    public LobbyVariables(boolean searching, int seconds, int connectedClients, boolean error, String errorMsg, boolean ready) {
        isSearching = searching;
        this.seconds = seconds;
        this.connectedClients = connectedClients;
        this.error = error;
        isReady = ready;
        this.errorMsg = errorMsg;
    }
}
