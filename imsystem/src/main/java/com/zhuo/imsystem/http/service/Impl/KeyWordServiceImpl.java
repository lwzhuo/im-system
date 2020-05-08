package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.service.KeyWordService;
import org.springframework.stereotype.Service;

// 关键词提取服务
@Service("keywordService")
public class KeyWordServiceImpl implements KeyWordService {
    public String[] getKeyWord(String text)
    {
        return new String[]{"aaa","bbb","ccc"};
    }
}
