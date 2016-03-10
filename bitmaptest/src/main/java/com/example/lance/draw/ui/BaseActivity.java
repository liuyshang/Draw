package com.example.lance.draw.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lance.draw.util.ViewInject;

import java.lang.reflect.Field;

/**
 * author: admin
 * time: 2016/3/9 17:28
 * e-mail: lance.cao@anarry.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(">>>", "onCreate");
        setContentView(getLayoutId());
        autoInjectAllField();
        init();
        setListener();
    }

    /**
     * 解释注解
     */
    private void autoInjectAllField() {
        try {
            Class<?> clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();//获取Activity中声明的字段
            for (Field field : fields) {
                //查看这个字段是否有我们自定义的注解类标志的
                if (field.isAnnotationPresent(ViewInject.class)) {
                    ViewInject inject = field.getAnnotation(ViewInject.class);
                    int id = inject.value();
                    if (id > 0) {
                        field.setAccessible(true);
                        field.set(this, this.findViewById(id)); //给我们要找的字段设置值
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(">>>", "onResume");
    }

    /**
     * 设置布局
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 监听
     */
    public abstract void setListener();

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(">>>", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(">>>", "onDestroy");
    }
}
