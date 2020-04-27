package com.zhuo.imsystem.http.service;

import java.io.RandomAccessFile;

public interface FileService {
    public String generateFileName();
    public boolean valid(String channelId);
    public boolean save(byte[] data,String filename,String channelId);
    public byte[] download(String channelId, String filename) throws Exception;
    public String getFileExtension(String filename);
}
