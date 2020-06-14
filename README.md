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
> `OpenGL ES 3.1` ：`Android 5.0(API 21)`和更高版本支持这个`API`规范`。  

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





## 三、离屏渲染

