package com.example.aviwe.pelebox.pojos;

public class DeviceList
{
    private int deviceId,dirtyFlag;
    private String deviceLocation,description;

    public DeviceList() {
    }

    public DeviceList(int deviceId, int dirtyFlag, String deviceLocation, String description) {
        this.deviceId = deviceId;
        this.dirtyFlag = dirtyFlag;
        this.deviceLocation = deviceLocation;
        this.description = description;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDirtyFlag() {
        return dirtyFlag;
    }

    public void setDirtyFlag(int dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
