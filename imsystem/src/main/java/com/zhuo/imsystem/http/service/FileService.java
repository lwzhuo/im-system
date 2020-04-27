package com.zhuo.imsystem.http.service;

public interface FileService {
    public String generateFileName();
    public boolean valid(String channelId);
    public boolean save(byte[] data,String filename,String channelId);
    public byte[] download(String channelId,String filename) throws Exception;
}
