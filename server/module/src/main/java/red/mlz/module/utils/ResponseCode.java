package red.mlz.module.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseCode {
    private static final Map<Integer, String> statusMap = new HashMap<Integer, String>();

    static {
        statusMap.put(1001, "OK");
        statusMap.put(1002, "失败");
        statusMap.put(1003, "没有登录哦~");
        statusMap.put(1010, "账号密码不匹配或账号不存在");

        //create user and forget password
        statusMap.put(2014, "账号尚未注册");


        //console error
        //article error

        statusMap.put(3051, "文章必填信息不能为空");
        statusMap.put(3052, "文章ID不正确");
        statusMap.put(3053, "分类不存在");

        statusMap.put(4003, "没有权限");
        statusMap.put(4004, "链接超时");
    }

    public static String getMsg(Integer code) {
        return statusMap.get(code);
    }
}
