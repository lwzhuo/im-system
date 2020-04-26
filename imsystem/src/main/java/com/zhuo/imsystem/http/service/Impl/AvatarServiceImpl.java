package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.service.AvatarService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service("avatarService")
public class AvatarServiceImpl implements AvatarService {
    public String generateAvatarName(){
        return String.valueOf(System.currentTimeMillis());
    }
    // 检查环境
    public boolean valid(String uid){
        try {
            // 检查是否存在用户头像目录
            String currentUserAvatarPathPath = ConstVar.AVATAR_BASE_PATH+uid;
            File file = new File(currentUserAvatarPathPath);
            boolean exist = file.exists();
            // 没有头像目录 开始创建
            if(!exist){
                return file.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String save(byte[] data,String filename,String path,String imageType,int width,int height){
        String filepath = path+filename+"."+imageType;
        InputStream is = new ByteArrayInputStream(data);
        try {
            Thumbnails.of(is).size(width > 70 ? width : width * 2, height > 70 ? height : height * 2).toFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filename+"."+imageType;
    }

    public byte[] download(String uid,String filename) throws Exception{
        String path = ConstVar.AVATAR_BASE_PATH+uid+"/"+filename;
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();
        return bytes;
    }
}
