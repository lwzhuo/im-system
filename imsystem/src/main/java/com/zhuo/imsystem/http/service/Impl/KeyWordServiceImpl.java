package com.zhuo.imsystem.http.service.Impl;

import com.hankcs.hanlp.HanLP;
import com.zhuo.imsystem.http.service.KeyWordService;
import org.springframework.stereotype.Service;

import java.util.List;

// 关键词提取服务
@Service("keywordService")
public class KeyWordServiceImpl implements KeyWordService {
    public String[] getKeyWord(String text)
    {
        List<String> keywordList = HanLP.extractKeyword(text, 5);
        return keywordList.toArray(new String[5]);
    }
}
