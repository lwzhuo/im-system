package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.http.service.FileService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service("fileService")
public class FileServiceImpl implements FileService {

    public String generateFileName(){
        return String.valueOf(System.currentTimeMillis());
    }

    public boolean valid(String channelId){
        try {
            // 检查是否存在channel目录
            String currentChannelPathPathPath = ConstVar.FILE_BASE_PATH+channelId;
            File file = new File(currentChannelPathPathPath);
            boolean exist = file.exists();
            // 没有channel目录 开始创建
            if(!exist){
                return file.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean save(byte[] data,String filename,String channelId){
        File file = new File(ConstVar.FILE_BASE_PATH+channelId+'/'+filename);
        OutputStream os = null;
        try {
            file.createNewFile();
            os = new FileOutputStream(file);
            os.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] download(String channelId, String filename) throws Exception{
        String path = ConstVar.FILE_BASE_PATH+channelId+"/"+filename;
        try {
            System.out.println(path);
            InputStream in = new FileInputStream(path);
            //available():获取输入流所读取的文件的最大字节数
            byte[] body = new byte[in.available()];
            //把字节读取到数组中
            in.read(body);
            return body;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public boolean isImage(String fileType) {
        // 狭义定义三种类型为图片
        if("image/jpeg".equalsIgnoreCase(fileType)) {
            return true;
        }
        if("image/png".equalsIgnoreCase(fileType)) {
            return true;
        }
        if("image/gif".equalsIgnoreCase(fileType)) {
            return true;
        }
        return false;
    }

    public String getFileExtension(String filename){
        String []res = filename.split("\\.");
        if(res.length>1)
            return res[1];
        else
            return "";
    }

}
