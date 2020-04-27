package com.zhuo.imsystem.commom.config;

public class ConstVar {
    // 队列名称
    public static int PRIVATE_CHANNEL_QUEUE = 1;
    public static int GROUP_CHANNEL_QUEUE = 2;
    public static int SYSTEM_CHANNEL_QUEUE = 3;

    // 消息类型
    public static int TEXT_MESSAGE_TYPE = 1;
    public static int EMOTION_MESSAGE_TYPE = 2;
    public static int PICTURE_MESSAGE_TYPE = 3;
    public static int FILE_MESSAGE_TYPE = 4;

    // 消息状态
    public static int MESSAGE_STATUS_NORMAL = 0;
    public static int MESSAGE_STATUS_DELETED = 1;

    // 私聊
    public static int PRIVATE_CHANNEL = 1;
    // 群聊
    public static int GROUP_CHALLEL = 2;

    // 聊天文本长度限制
    public static int MAX_TEXT_LENGTH = 300;

    // 头像大小限制 单位KB
    public static int MAX_AVATAR_SIZE = 2048;

    // 文件大小限制 单位KB
    public static int MAX_FILE_SIZE = 2048;

    // 头像基准路径
    public static String AVATAR_BASE_PATH = "/Users/zhuo/Documents/im-system-source/avatar/";

    // 文件基准路径
    public static String FILE_BASE_PATH = "/Users/zhuo/Documents/im-system-source/file/";
}
