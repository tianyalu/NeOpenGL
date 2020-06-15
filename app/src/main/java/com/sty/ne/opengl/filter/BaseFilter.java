package com.sty.ne.opengl.filter;

import android.content.Context;
import android.opengl.GLES11Ext;

import com.sty.ne.opengl.util.BufferHelper;
import com.sty.ne.opengl.util.ShaderHelper;
import com.sty.ne.opengl.util.TextResourceReader;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class BaseFilter {
    protected int mVertexSourceId;
    protected int mFragmentSourceId;
    protected int vPosition;
    protected int vCoord;
    protected int vMatrix;
    protected int vTexture;

    protected FloatBuffer mVertexBuffer; //顶点坐标
    protected FloatBuffer mTextureBuffer; //纹理坐标

    protected int mProgramId;
    protected int mHeight;
    protected int mWidth;

    public BaseFilter(Context context, int vertexSourceId, int fragmentSourceId) {
        mVertexSourceId = vertexSourceId;
        mFragmentSourceId = fragmentSourceId;

        //参考：https://www.jianshu.com/p/c4dda6884655  opengl世界坐标系.png
        float[] VERTEX = {
                -1.0f, -1.0f, //左下
                1.0f, -1.0f, //右下
                -1.0f, 1.0f, //左上
                1.0f, 1.0f   //右上
        };
        mVertexBuffer = BufferHelper.getFloatBuffer(VERTEX);

        //参考：https://www.jianshu.com/p/c4dda6884655  Android屏幕坐标系.png
        float[] TEXTURE = {
//                0.0f, 1.0f, //左下
//                1.0f, 1.0f, //右下
//                0.0f, 0.0f, //左上
//                1.0f, 0.0f  //右上

                //因为是反的，所以要逆时针旋转180度，并且左右镜像。
                0.0f, 0.0f, //左下
                1.0f, 0.0f, //右下
                0.0f, 1.0f, //左上
                1.0f, 1.0f  //右上
        };
        mTextureBuffer = BufferHelper.getFloatBuffer(TEXTURE);

        init(context);
    }

    private void init(Context context) {
        String vertexSource = TextResourceReader.readTextFileFromResource(context,
                mVertexSourceId);  //顶点着色器源码
        String fragmentSource = TextResourceReader.readTextFileFromResource(context,
                mFragmentSourceId);  //片元着色器源码

        int vertexShaderId = ShaderHelper.compileVertexShader(vertexSource);
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentSource);

        mProgramId = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);

        //通过变量索引给变量赋值
        //获取变量的索引
        vPosition = glGetAttribLocation(mProgramId, "vPosition");
        vCoord = glGetAttribLocation(mProgramId, "vCoord");
        vMatrix = glGetUniformLocation(mProgramId, "vMatrix");
        vTexture = glGetUniformLocation(mProgramId, "vTexture");
    }

    public void onReady(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int onDrawFrame(int textureId) {
        glViewport(0, 0, mWidth, mHeight); //设置视窗大小
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
        //glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);

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
        return textureId;
    }

    public void release() {
        glDeleteProgram(mProgramId);
    }
}
