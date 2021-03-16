package com.platform.house.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-04 11:21
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestParameterWrapper(HttpServletRequest request) {
        super(request);

        this.params.putAll(request.getParameterMap());
    }

    @Override
    public Enumeration<String> getParameterNames(){
        Vector<String> nameList = new Vector<String>();
        for(Map.Entry<String,String[]> entry: params.entrySet()){
            nameList.add(entry.getKey());
        }
        return nameList.elements();
    }


    /**
     * 重写getParameter，代表参数从当前类中的map获取
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 同上
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    /**
     * 添加参数
     * @param name
     * @param value
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            System.out.println(value);
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }
}
