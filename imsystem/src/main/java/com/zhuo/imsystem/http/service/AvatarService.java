package com.zhuo.imsystem.http.service;

public interface AvatarService {
    public String generateAvatarName();
    public boolean valid(String uid);
    public String save(byte[] data,String filename,String path,String imageType,int width,int height);
    public void download(String path,String filename);
}
