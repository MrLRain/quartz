package w3cblogs.club.service;

import org.springframework.stereotype.Service;


/**
 * 测试自动注入 和 手动注入的点
 */
@Service
public class TestService {

    public void idFind(String id){
        System.out.println("TestService id = " + id);
    }
}
