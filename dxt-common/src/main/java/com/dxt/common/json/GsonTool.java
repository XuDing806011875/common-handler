package com.dxt.common.json;

import com.google.gson.Gson;

public class GsonTool implements IJsonApi<String>{

    public static GsonTool INSTANCE = new GsonTool();
    private static Gson gson = new Gson();

    /**
     * 将对象转换为json
     * @param t
     * @return
     */
    public String encode(Object t) {
        return gson.toJson(t);
    }

    /**
     * 将json按clazz转换为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T decode(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * 获取GsonTool实例
     * @return
     */
    public IJsonApi getInstance() {
        return INSTANCE;
    }
}
