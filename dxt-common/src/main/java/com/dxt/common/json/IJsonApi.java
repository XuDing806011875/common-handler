package com.dxt.common.json;

public interface IJsonApi<R> {

    /**
    * 将对象转换为R
    * @param paramObject
    * @return
    */
    R encode(Object paramObject);

    /**
    * 将R转换为对象
    * @param paramR
    * @param paramClass
    * @param <T>
    * @return
    */
    <T> T decode(R paramR, Class<T> paramClass);
}
