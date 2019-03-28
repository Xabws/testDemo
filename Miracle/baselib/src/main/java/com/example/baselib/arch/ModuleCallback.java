package com.example.baselib.arch;

/**
 * Module异步调用的回调接口
 */
public interface ModuleCallback<T> {

    void onModuleCallback(ModuleResult<T> result);

}
