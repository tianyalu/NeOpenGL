package com.sty.ne.opengl.filter;

import android.content.Context;
import android.opengl.GLES11Ext;

import com.sty.ne.opengl.R;
import com.sty.ne.opengl.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class ScreenFilter {
    private int vPosition;
    private int vCoord;
    private int vMatrix;
    private int vTexture;

    private FloatBuffer mVertexBuffer; //顶点坐标
    private FloatBuffer mTextureBuffer; //纹理坐标

    private int mProgramId;
    private int mHeight;
    private int mWidth;

    public ScreenFilter(Context context) {
        String vertexSource = TextResourceReader.readTextFileFromResource(context,
                R.raw.camera_vetex);  //顶点着色器源码
        String fragmentSource = TextResourceReader.readTextFileFromResource(context,
                R.raw.camera_fragment);  //片元着色器源码

        /**
         * 1. 配置顶点着色器
         */
        //1.1 创建顶点着色器
        int vShaderId = glCreateShader(GL_VERTEX_SHADER);
        //1.2 绑定着色器源码到着色器
        glShaderSource(vShaderId, vertexSource);
        //1.3 编译着色器代码
        glCompileShader(vShaderId);
        //1.4 判断编译是否成功
        int[] status = new int[1];
        glGetShaderiv(vShaderId, GL_COMPILE_STATUS, status, 0);
        if(status[0] != GL_TRUE) {
            throw new IllegalStateException("顶点着色器配置失败");
        }

        /**
         * 2. 配置片元着色器
         */
        //2.1 创建片元着色器
        int fShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        //2.2 绑定着色器源码到着色器
        glShaderSource(fShaderId, fragmentSource);
        //2.3 编译着色器代码
        glCompileShader(fShaderId);
        //2.4 判断编译是否成功
        glGetShaderiv(fShaderId, GL_COMPILE_STATUS, status, 0);
        if(status[0] != GL_TRUE) {
            throw new IllegalStateException("片元着色器配置失败");
        }

        /**
         * 3. 配置片元着色器程序
         */
        //3.1 创建一下 OpenGL 程序
        mProgramId = glCreateProgram();
        //3.2 将前面创建的顶点和片元着色器附加到程序上
        glAttachShader(mProgramId, vShaderId);
        glAttachShader(mProgramId, fShaderId);
        //3.3 链接着色器
        glLinkProgram(mProgramId);
        //3.4 判断链接是否成功
        glGetProgramiv(mProgramId, GL_LINK_STATUS, status, 0);
        if(status[0] != GL_TRUE) {
            throw new IllegalStateException("着色器程序链接失败");
        }

        /**
         * 4. 释放、删除着色器
         */
        glDeleteShader(vShaderId);
        glDeleteShader(fShaderId);

        /**
         * 5. 通过变量索引给变量赋值
         */
        //5.1 获取变量的索引
        vPosition = glGetAttribLocation(mProgramId, "vPosition");
        vCoord = glGetAttribLocation(mProgramId, "vCoord");
        vMatrix = glGetUniformLocation(mProgramId, "vMatrix");
        vTexture = glGetUniformLocation(mProgramId, "vTexture");

        //5.2 顶点坐标 缓冲区内存分配
        mVertexBuffer = ByteBuffer.allocateDirect(4*2*4) //坐标数 * 数据类型 * 占字节数
                .order(ByteOrder.nativeOrder()) //使用本地字节序
                .asFloatBuffer();
        mVertexBuffer.clear();

        //参考：https://www.jianshu.com/p/c4dda6884655  opengl世界坐标系.png
        float[] v = {
                -1.0f, -1.0f, //左下
                1.0f, -1.0f, //右下
                -1.0f, 1.0f, //左上
                1.0f, 1.0f   //右上
        };
        //5.3 顶点坐标赋值
        mVertexBuffer.put(v);

        //5.4 纹理坐标 缓冲区内存分配
        mTextureBuffer = ByteBuffer.allocateDirect(4*2*4) //坐标数 * 数据类型 * 占字节数
                .order(ByteOrder.nativeOrder()) //使用本地字节序
                .asFloatBuffer();
        mTextureBuffer.clear();

        //参考：https://www.jianshu.com/p/c4dda6884655  Android屏幕坐标系.png
        float[] t = {
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
        //5.5 纹理坐标赋值
        mTextureBuffer.put(t);
    }

    public void onReady(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public void onDrawFrame(int textureId, float[] mtx) {
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
        glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);

        //vTexture
        //激活图层
        glActiveTexture(GL_TEXTURE0);
        //绑定纹理
        //glBindTexture(GL_TEXTURE_2D);
        glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        glUniform1i(vTexture, 0);

        //通知OpenGL绘制
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }
}
