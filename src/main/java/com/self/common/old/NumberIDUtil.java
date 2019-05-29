package com.self.common.old;

import com.novel.cn.dao.mapper.AuthorInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by lizhong on 2018/5/9.
 */
@Component
public class NumberIDUtil {

    @Autowired
    private AuthorInfoMapper authorInfoMapper;
    @Value("${author.id.length}")
    private Integer length;

    private static Long numId = 0L;

    @PostConstruct
    private void initNumberId() {
        Double initNumId = Math.pow(10, length-1);
        numId = authorInfoMapper.getMaxId();
        if (numId == null || numId <= initNumId) {
            numId = Long.parseLong(initNumId.intValue() + "");
        }
    }

    public synchronized static Long getNumId() {
        return ++numId;
    }

}
