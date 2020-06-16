# `OpenGL SL`基础--摄像头预览--离屏渲染

[TOC]

## 一、`OpenGL` 基础知识

`OpenGL(Open Graphics Library)`，图形领域的工业标准，是一套跨编程语言、跨平台、专业的图形编程（软件）接口。它用于二维、三维图像，是一个功能强大，调用方便的底层图形库。	

`OpenGL` 与硬件无关，可以在不同的平台如`Windows`、`Linux`、`Mac`、`Android`、`IOS`之间进行移植，因此支持`OpenGL`的软件具有很好的移植性，可以获得非常广泛的应用。

### 1.1 `Android OpenGL ES`

`Android OpenGL ES` 是针对手机、`PDA`和游戏主机等嵌入式设备设计的`OpenGL API`子集。 

> `OpenGL ES 1.0` 和`1.1`：`Android 1.0`和更高版本支持这个`API`规范；  
> `OpenGL ES 2.0` ：`Android 2.2(API 8)`和更高版本支持这个`API`规范；  
> `OpenGL ES 3.0` ：`Android 4.3(API 18)`和更高版本支持这个`API`规范；  
> `OpenGL ES 3.1` ：`Android 5.0(API 21)`和更高版本支持这个`API`规范。  

`OpenGL`需要由设备制造商提供实现支持，目前广泛支持的是2.0 。`Android`需要在`AndroidManifest.xml`文件中添加如下配置：  

```xml
    <uses-feature android:glEsVersion="0x00020000" android:required="true" />
```

#### 1.1.1 `GLSurfaceView`

> 继承至`SurfaceView`，它内嵌的`surface`专门负责`OpenGL`渲染；  
> 管理`Surface`与`EGL`；  
> 允许自定义渲染器（`render`）；  
> 让渲染器在独立的线程里工作，和`UI`线程分离；  
> 支持按需渲染（`on-demand`）和连续渲染（`continuous`）。  

#### 1.1.2 `EGL`

`OpenGL`是一个跨平台的操作`GPU`的`API`，但`OpenGL`需要与本地视窗系统进行交互，这就需要一个中间控制层，`EGL`就是连接`OpenGL ES` 和本地窗口系统的接口，引入`EG`L就是为了屏蔽不同平台上的区别。

### 1.2 `OpenGL`绘制流程

![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/opengl_draw_process.png)   

### 1.3 `OpenGL`可编程管线

![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/opengl_pipe.png)   

### 1.4 坐标系

#### 1.4.1 `OpenGL`世界坐标系

![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/opengl_world_coordinate_system.png)   

#### 1.4.2 `OpenGL`纹理坐标系

![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/opengl_texture_coordinate_system.png)

#### 1.4.3 `Android`屏幕坐标系

![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/android_screen_coordinate_system.png)      

### 1.5 着色器

着色器（`Shader`）是运行在GPU上的小程序。

* 顶点着色器（`vertex shader`）：如何处理顶点、法线等数据的小程序；  

* 片元着色器（`fragment shader`）：如何处理光、阴影、遮挡、环境等等对物体表面的影响，最终生成一副图像的小程序。  

### 1.6 `GLSL`

`OpenGL`：着色语言（`OpenGL Shading Language`）。
![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/glsl.png)   

#### 1.6.1 数据类型

| 类型      | 描述                                       |
| --------- | ------------------------------------------ |
| float     | 浮点型                                     |
| vec2      | 含两个浮点型数据的向量                     |
| vec4      | 含四个浮点型数据的向量（xyzw, rgba, stpq） |
| sampler2D | 2D纹理采样器（代表一层纹理）               |

#### 1.6.2 修饰符

| 类型      | 描述                                                         |
| --------- | ------------------------------------------------------------ |
| attribute | 属性变量，只能用于顶点着色器中，一般用该变量来表示一些顶点数据，如：顶点坐标、纹理坐标、颜色等。 |
| uniforms  | 一致变量，在着色器执行期间一致变量的值是不变的。与const常量不同的是这个值在编译时期是未知的，它是由着色器外部初始化的。 |
| varying   | 易变变量，是从顶点着色器传递到片元着色器的数据变量。         |

#### 1.6.3 内建函数

`texture2D`（采样器，坐标）采样指定位置的纹理。

#### 1.6.4 内建变量

| 变量         | 描述                                 |
| ------------ | ------------------------------------ |
| gl_Position  | vec4类型，表示顶点着色器中顶点的位置 |
| gl_FragColor | vec4类型，表示片元着色器中颜色       |

#### 1.6.5 其他

| 名称              | 描述   |
| ----------------- | ------ |
| precision lowp    | 低精度 |
| precision mediump | 中精度 |
| precision highp   | 高精度 |

### 1.7 参考&学习资料

* `OpenGL`系统性学习网站：

  中文网站：

  ​     [https://learnopengl-cn.readthedocs.io/zh/latest/](https://learnopengl-cn.readthedocs.io/zh/latest/)

  ​     [https://learnopengl-cn.github.io/](https://learnopengl-cn.github.io/)

  英文网站：

  ​    [https://learnopengl.com/](https://learnopengl.com/)

* 本文参考：    

  [OpenGL](https://www.jianshu.com/p/c4dda6884655)  
  [The OpenGL ES Shading Language-基础篇](https://www.jianshu.com/p/f1a86ac46b4d)  
  [The OpenGL ES Shading Language-变量与类型篇](https://www.jianshu.com/p/86285678d2c1)    

* 插件：  
  本文使用了`GLSL Support`插件，如果在插件市场搜索不到的话，可以考虑使用`resources`目录下的`GLSL4idea.jar`；同时该目录下的`PDF`文件也可作为学习资料。

## 二、`OpenGL SL` 实现相机预览

> 代码在 `camera_preview` 分支。

### 2.1 总体实现思路

核心点在于`GLSurfaceView`初始化时为其设置自定义的渲染器`MyGLRender`,该渲染器实现了`GLSurfaceView.Renderer`和`SurfaceTexture.OnFrameAvailableListener`接口，主要工作都是在这些接口中的实现方法中实现的：

> 1. `onSurfaceCreated()`：`Surface`创建时，初始化`CameraHelper`，准备纹理画布，设置画布可用监听器，初始化`ScreenFilter`；
> 2. `onSurfaceChanged()`：`Surface`改变时，`CameraHelper`设置纹理画布，并开始预览，`ScreenFilter`调用`onReady(w,h)`方法传入宽高；
> 3. `onDrawFrame()`：绘制时，清屏，纹理画布更新，获取变换矩阵，调用`ScreenFilter`的`onDrawFrame()`方法绘制；
> 4. `onFrameAvailable()`：纹理画布可用时，调用`GLSurfaceView.requestRender()`方法请求渲染。

### 2.2 详细步骤

#### 2.2.1 `Surface`创建时

```java
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
```

`ScreenFilter`初始化步骤：

> 1. 在`app/src/main/res/raw/`目录下编写顶点着色器和片元着色器源码文件；
> 2. 从顶点着色器和片元着色器源码文件中读取源码变成`String`类型；
> 3. 配置顶点着色器；  
>    a. 创建顶点着色器；  
>    b. 绑定顶点着色器源码到着色器；  
>    c. 编译着色器代码；  
>    d. 判断编译是否成功；  
> 4. 配置顶点着色器；  
>    a. 创建片元着色器；  
>    b. 绑定片元着色器源码到着色器；   
>    c. 编译着色器代码；   
>    d. 判断编译是否成功；     
> 5. 创建`OpenGL`程序；
> 6. 将创建的顶点和片元着色器附加到该程序上；
> 7. 链接着色器；
> 8. 判断链接是否成功；
> 9. 释放、删除着色器;
> 10. 通过变量索引给变量赋值；  
>     a. 获取变量的索引；  
>     b. 顶点坐标 缓冲区内存分配；  
>     c. 顶点坐标赋值；  
>     d. 纹理坐标 缓冲区内存分配；  
>     e. 纹理坐标赋值 。 

#### 2.2.2 `Surface`改变时

```java
/**
 * Surface 发生改变时回调
 * @param gl10 1.0 api预留参数
 * @param width
 * @param height
 */
@Override
public void onSurfaceChanged(GL10 gl10, int width, int height) {
  mCameraHelper.startPreview(mSurfaceTexture);
  mScreenFilter.onReady(width, height);
}
```

摄像头预览时主要是设置纹理来实现离屏渲染：

```java
//离屏渲染
mCamera.setPreviewTexture(surfaceTexture);
```

#### 2.2.3 绘制时

```java
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
```

`ScreenFilter`绘制：

```java
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
```

#### 2.2.4 纹理画布可用时

```java
/**
 * 画布有有效数据时回调
 * @param surfaceTexture
 */
@Override
public void onFrameAvailable(SurfaceTexture surfaceTexture) {
  myGLSurfaceView.requestRender(); //请求渲染
}
```

## 三、离屏渲染

> 代码在 `master` 分支。

离屏渲染主要依赖`FBO`实现。

`FBO（Frame Buffer Object）`：帧缓冲对象（1个存储单元对应屏幕上1个像素，整个帧缓冲对应1帧图像）。默认情况下，我们在`GLSurfaceView`中绘制的结果是直接显示到屏幕上的，然而实际中有很多情况并不需要渲染到屏幕上，这时候使用`FBO`就可以很方便地实现这类需求。`FBO`可以让我们的渲染不直接渲染到屏幕上，而是渲染到离屏Buffer中。

### 3.1 思路

前面我们创建了一个`ScreenFilter`类用来封装将摄像头数据显示到屏幕上，然而我们需要在显示之前增加各种“效果”，如果我们只存在一个`ScreenFilter`，那么所有的“效果”都会积压在这个类中，同时也需要大量的`if else`来判断是否开启“效果”。

我们可以将每种“效果”写到单独的一个`Filter`中去，并且在`ScreenFilter`之前的所有`Filter`都不需要显示到屏幕中，所以在`ScreenFilter`之前都将其使用`FBO`进行缓存。

![image](https://github.com/tianyalu/NeOpenGL/raw/master/show/fbo_structure.png)   

### 3.2 注意事项

摄像头画面经过`FBO`的缓存的时候，我们再从`FBO`绘制到屏幕，这时候就不需要在使用`samplerExternalOES`了。这也意味着`ScreenFilter`使用的采样器就是正常的`sample2D`，也不需要`#extension GL_OES_EGL_image_external : require`。

然而在最原始的状态下是没有开启任何效果的，所有`ScreenFilter`就比较尴尬：

> 开启效果：使用`sample2D`
> 未开启效果：使用`sampleExternalOES`

那么就需要在`ScreenFilter`中使用`if else`来进行判断，但比较麻烦，这里我们采用如下方式：

> 从摄像头使用的纹理首先绘制到`CameraFilter`的`FBO`中，这样无论是否开启效果，`ScreenFilter`都是以`sample2D`来进行采用的了。

### 3.3 参考资料

本文参考：  

[FBO 帧缓存对象](https://www.jianshu.com/p/8243b517e96a)  

[OpenGL 纹理篇](https://www.jianshu.com/p/1b0ecbd671ff)

