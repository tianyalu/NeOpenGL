package com.sty.ne.opengl;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.sty.ne.opengl.filter.ScreenFilter;
import com.sty.ne.opengl.util.CameraHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

class MyGLRender implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private CameraHelper mCameraHelper;
    private MyGLSurfaceView myGLSurfaceView;
    private int[] mTextureID;
    private SurfaceTexture mSurfaceTexture;
    private ScreenFilter mScreenFilter;
    private float[] mtx = new float[16];

    public MyGLRender(MyGLSurfaceView myGLSurfaceView) {
        this.myGLSurfaceView = myGLSurfaceView;
    }

    /**
     * Surface创建时回调
     * @param gl10 1.0 api预留参数
     * @param eglConfig
     */
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mCameraHelper = new CameraHelper((Activity) myGLSurfaceView.getContext());
        //准备画布
        mTextureID = new int[1];
        //第三个参数表示你要使用mTextureID数组中那个ID的索引
        glGenTextures(mTextureID.length, mTextureID, 0);

        mSurfaceTexture = new SurfaceTexture(mTextureID[0]);
        mSurfaceTexture.setOnFrameAvailableListener(this);

        mScreenFilter = new ScreenFilter(myGLSurfaceView.getContext());
    }

    /**
     * Surface 发生改变时回调
     * @param gl10 1.0 api预留参数
     * @param i
     * @param i1
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mCameraHelper.startPreview(mSurfaceTexture);
        mScreenFilter.onReady(width, height);
    }

    /**
     * 绘制一帧图像时回调
     * 注意：该方法中必须进行绘制操作
     * （返回后，交换渲染缓冲区，如果不绘制，会导致闪屏）
     * @param gl10 1.0 api预留参数
     */
    @Override
    public void onDrawFrame(GL10 gl10) {
        glClearColor(255, 0, 0, 0); //设置清屏颜色
        glClear(GL_COLOR_BUFFER_BIT); //颜色缓冲区

        //绘制相机图像数据
        mSurfaceTexture.updateTexImage();
        mSurfaceTexture.getTransformMatrix(mtx);
        mScreenFilter.onDrawFrame(mTextureID[0], mtx);
    }

    /**
     * 画布有有效数据时回调
     * @param surfaceTexture
     */
    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        myGLSurfaceView.requestRender();
    }
}
