package com.zhuo.imsystem.http.service;

import java.awt.image.BufferedImage;

public interface AvatarService {
    public String generateAvatarName();
    public boolean valid(String uid);
    public String save(byte[] data,String filename,String path,String imageType,int width,int height);
    public byte[] download(String uid,String filename) throws Exception;
}
