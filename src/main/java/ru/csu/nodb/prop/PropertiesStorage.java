package ru.csu.nodb.prop;

import java.net.URI;

public class PropertiesStorage {

    public static PropertiesStorage getInstance() {
        return ourInstance;
    }

    private PropertiesStorage() {
    }

    public int getPort() {
        return port;
    }

    protected void setPort(int port) {
        this.port = port;
    }

    public boolean isMaster() {
        return master;
    }

    protected void setMaster(boolean master) {
        this.master = master;
    }

    public URI getFirstSlaveUrl() {
        return firstSlaveUrl;
    }

    protected void setFirstSlaveUrl(URI firstSlaveUrl) {
        this.firstSlaveUrl = firstSlaveUrl;
    }

    public URI getSecondSlaveUrl() {
        return secondSlaveUrl;
    }

    protected void setSecondSlaveUrl(URI secondSlaveUrl) {
        this.secondSlaveUrl = secondSlaveUrl;
    }

    public boolean isAsyncMode() {
        return asyncMode;
    }

    public void setAsyncMode(boolean asyncMode) {
        this.asyncMode = asyncMode;
    }

    private static PropertiesStorage ourInstance = new PropertiesStorage();
    private int port;
    private boolean master;
    private URI firstSlaveUrl;
    private URI secondSlaveUrl;
    private boolean asyncMode;
}
