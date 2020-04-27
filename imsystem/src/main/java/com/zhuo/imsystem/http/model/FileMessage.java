package com.zhuo.imsystem.http.model;

public class FileMessage {
    private String newFileName;
    private String origionFileName;
    private long size;
    private String contentType;

    public FileMessage(String newFileName, String origionFileName, long size, String contentType) {
        this.newFileName = newFileName;
        this.origionFileName = origionFileName;
        this.size = size;
        this.contentType = contentType;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getOrigionFileName() {
        return origionFileName;
    }

    public void setOrigionFileName(String origionFileName) {
        this.origionFileName = origionFileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
