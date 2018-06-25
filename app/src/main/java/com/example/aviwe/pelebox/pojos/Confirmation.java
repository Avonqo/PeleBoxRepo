package com.example.aviwe.pelebox.pojos;


public class Confirmation
{
    public int MediPackId;
    public int DirtyFlag;


    public Confirmation(int mediPackId, int dirtyFlag) {
        MediPackId = mediPackId;
        DirtyFlag = dirtyFlag;
    }

    public int getMediPackId() {
        return MediPackId;
    }

    public void setMediPackId(int mediPackId) {
        MediPackId = mediPackId;
    }

    public int getDirtyFlag() {
        return DirtyFlag;
    }

    public void setDirtyFlag(int dirtyFlag) {
        DirtyFlag = dirtyFlag;
    }
}