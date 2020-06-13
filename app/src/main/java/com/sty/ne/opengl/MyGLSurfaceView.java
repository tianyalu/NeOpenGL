package com.sty.ne.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGL();
    }

    public void initGL() {
        setEGLContextClientVersion(2); //设置egl版本
        setRenderer(new MyGLRender(this)); //设置自定义渲染器
        setRenderMode(RENDERMODE_WHEN_DIRTY); //设置按需渲染模式
    }
}
