package com.example.aviwe.pelebox.pojos;

import java.io.Serializable;


public class MediPackClient implements Serializable
{
    private int MediPackId;
    private String PatientFisrtName,PatientLastName,PatientCellphone,MediPackBarcode,PatientRSA,ManifestNumber,ScannedInDateTime,ScannedOutDateTime,MediPackDueDateTime;
    private int DeviceId,inUserId,OutUserId,DirtyFlag,MediPackStatusId;

    public MediPackClient() {
    }

    public MediPackClient(int mediPackId, String patientFisrtName, String patientLastName, String patientCellphone, String mediPackBarcode, String mediPackDueDateTime) {
        MediPackId = mediPackId;
        PatientFisrtName = patientFisrtName;
        PatientLastName = patientLastName;
        PatientCellphone = patientCellphone;
        MediPackBarcode = mediPackBarcode;
        MediPackDueDateTime =mediPackDueDateTime;
    }
    public MediPackClient( String patientFisrtName, String patientLastName, String patientCellphone, String mediPackBarcode) {

        PatientFisrtName = patientFisrtName;
        PatientLastName = patientLastName;
        PatientCellphone = patientCellphone;
        MediPackBarcode = mediPackBarcode;

    }

    public MediPackClient(int mediPackId, String patientFisrtName, String patientLastName, String patientCellphone, String mediPackBarcode,int MediDays) {
        MediPackId = mediPackId;
        PatientFisrtName = patientFisrtName;
        PatientLastName = patientLastName;
        PatientCellphone = patientCellphone;
        MediPackBarcode = mediPackBarcode;
    }

    public MediPackClient(int mediPackId, String patientFisrtName, String patientLastName, String patientCellphone, String mediPackBarcode, String patientRSA, String manifestNumber,
                          String scannedInDateTime, String scannedOutDateTime, int deviceId, int inUserId, int outUserId, int dirtyFlag, int mediPackStatusId,String mediPackDueDateTime) {
        MediPackId = mediPackId;
        PatientFisrtName = patientFisrtName;
        PatientLastName = patientLastName;
        PatientCellphone = patientCellphone;
        MediPackBarcode = mediPackBarcode;
        PatientRSA = patientRSA;
        ManifestNumber = manifestNumber;
        ScannedInDateTime = scannedInDateTime;
        ScannedOutDateTime = scannedOutDateTime;
        DeviceId = deviceId;
        this.inUserId = inUserId;
        OutUserId = outUserId;
        DirtyFlag = dirtyFlag;
        MediPackStatusId = mediPackStatusId;
        MediPackDueDateTime = mediPackDueDateTime;
    }

    public MediPackClient(String patientFisrtName, String patientLastName, String patientRSA, String mediPackBarcode, String patientCellphone, String mediDueDate,int status) {
        PatientFisrtName = patientFisrtName;
        PatientLastName = patientLastName;
        PatientCellphone = patientCellphone;
        MediPackBarcode = mediPackBarcode;
        MediPackDueDateTime = mediDueDate;
        PatientRSA = patientRSA;
        MediPackStatusId=status;

    }

    public int getMediPackId() {
        return MediPackId;
    }

    public void setMediPackId(int mediPackId) {
        MediPackId = mediPackId;
    }

    public String getPatientFisrtName() {
        return PatientFisrtName;
    }

    public void setPatientFisrtName(String patientFisrtName) {
        PatientFisrtName = patientFisrtName;
    }

    public String getPatientLastName() {
        return PatientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        PatientLastName = patientLastName;
    }

    public String getPatientCellphone() {
        return PatientCellphone;
    }

    public void setPatientCellphone(String patientCellphone) {
        PatientCellphone = patientCellphone;
    }

    public String getMediPackBarcode() {
        return MediPackBarcode;
    }

    public void setMediPackBarcode(String mediPackBarcode) {
        MediPackBarcode = mediPackBarcode;
    }

    public String getPatientRSA() {
        return PatientRSA;
    }

    public void setPatientRSA(String patientRSA) {
        PatientRSA = patientRSA;
    }

    public String getManifestNumber() {
        return ManifestNumber;
    }

    public void setManifestNumber(String manifestNumber) {
        ManifestNumber = manifestNumber;
    }

    public String getScannedInDateTime() {
        return ScannedInDateTime;
    }

    public void setScannedInDateTime(String scannedInDateTime) {
        ScannedInDateTime = scannedInDateTime;
    }

    public String getScannedOutDateTime() {
        return ScannedOutDateTime;
    }

    public void setScannedOutDateTime(String scannedOutDateTime) {
        ScannedOutDateTime = scannedOutDateTime;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }

    public int getInUserId() {
        return inUserId;
    }

    public void setInUserId(int inUserId) {
        this.inUserId = inUserId;
    }

    public int getOutUserId() {
        return OutUserId;
    }

    public void setOutUserId(int outUserId) {
        OutUserId = outUserId;
    }

    public int getDirtyFlag() {
        return DirtyFlag;
    }

    public void setDirtyFlag(int dirtyFlag) {
        DirtyFlag = dirtyFlag;
    }

    public int getMediPackStatusId() {
        return MediPackStatusId;
    }

    public void setMediPackStatusId(int mediPackStatusId) {
        MediPackStatusId = mediPackStatusId;
    }

    public String getMediPackDueDateTime() {
        return MediPackDueDateTime;
    }

    public void setMediPackDueDateTime(String mediPackDueDateTime) {
        MediPackDueDateTime = mediPackDueDateTime;
    }
}
