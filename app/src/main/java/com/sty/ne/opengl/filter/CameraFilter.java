package com.sty.ne.opengl.filter;

import android.content.Context;

import com.sty.ne.opengl.R;
import com.sty.ne.opengl.util.TextureHelper;

import static android.opengl.GLES20.*;

/**
 * 不需要渲染到屏幕（而是写入到发BO缓冲中）
 */
public class CameraFilter extends BaseFilter {

    private int[] mFrameBuffers;
    private float[] matrix;
    private int[] mFrameBufferTextures;

    public CameraFilter(Context context) {
        super(context, R.raw.camera_vetex, R.raw.camera_fragment);
    }

    @Override
    public void onReady(int width, int height) {
        super.onReady(width, height);
        //创建FBO（虚拟的，看不见的离屏的一个屏幕）
        //int n: FBO的个数
        //int[] framebuffers: 用来保存FBO id 的数组
        //int offset: 从数组的第几个id来开始保存
        mFrameBuffers = new int[1];
        glGenFramebuffers(mFrameBuffers.length, mFrameBuffers, 0);

        //创建属于FBO的纹理（需要配置）
        mFrameBufferTextures = new int[1];
        TextureHelper.genTextures(mFrameBufferTextures);

        //让FBO与 上面生成的FBO纹理发生关系
        //绑定
        glBindTexture(GL_TEXTURE_2D, mFrameBufferTextures[0]);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, null);
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffers[0]);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
                mFrameBufferTextures[0], 0);

        //解绑
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     *
     * @param textureId 摄像头的纹理id
     * @return
     */
    @Override
    public int onDrawFrame(int textureId) {
        glViewport(0, 0, mWidth, mHeight); //设置视窗大小
        //绑定 FBO （否则会渲染到屏幕）
        glBindFramebuffer(GL_FRAMEBUFFER, mFrameBuffers[0]);

        glUseProgram(mProgramId);
        //画画
        //顶点坐标赋值
        mVertexBuffer.position(0);
        //传值
        glVertexAttribPointer(vPosition, 2, GL_FLOAT, false, 0, mVertexBuffer);
        //激活
        glEnableVertexAttribArray(vPosition);

        //纹理坐标
        mTextureBuffer.position(0);
        glVertexAttribPointer(vCoord, 2, GL_FLOAT, false, 0, mTextureBuffer);
        glEnableVertexAttribArray(vCoord);

        //变换矩阵
        glUniformMatrix4fv(vMatrix, 1, false, matrix, 0);

        //vTexture
        //激活图层
        glActiveTexture(GL_TEXTURE0);
        //绑定纹理
        glBindTexture(GL_TEXTURE_2D, textureId);
        //glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        glUniform1i(vTexture, 0);

        //通知OpenGL绘制
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        //解绑
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        //return textureId; //注意这里,不能反悔摄像头的纹理id，而是返回与 FBO 绑定了的纹理id
        return mFrameBufferTextures[0];
    }

    public void setMatrix(float[] mtx) {
        this.matrix = mtx;
    }

    private void releaseFBO() {
        if(null != mFrameBufferTextures) {
            glDeleteTextures(1, mFrameBufferTextures, 0);
            mFrameBufferTextures = null;
        }
        if(null != mFrameBuffers) {
            glDeleteFramebuffers(1, mFrameBuffers, 0);
            mFrameBuffers = null;
        }
    }

    @Override
    public void release() {
        super.release();
        releaseFBO();
    }
}
