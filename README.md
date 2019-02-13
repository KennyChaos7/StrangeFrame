# StrangeFrame
* [简要介绍](#简要介绍)


  简易android框架（也许有一天会支持成为Java框架）

> 依然在龟速写中

> 将会将会逐步完善各个文档和注释

* 简要介绍
    此框架将包含以下功能（加粗部分为已完成）

    1.  [**控件及其事件注解**](#控件注解及其事件)
    2.  **线程管理**(*将补充范例)
    3.  **订阅和发布事件**(*将补充范例)
    4.  **错误捕获机制**(*将补充范例)
    5.  [数据库操作]

##  控件注解及其事件

*   采用jvm的类加载机制来将控件和对应的Activity进行绑定
*   通过反射机制中的代理*Proxy.getInvocationHandler*和*Proxy.newProxyInstance*来把控件与事件进行绑定
````
    public @interface V {
        /**
         * view id
         * @return
         */
        int value();
    }

    ...


    public @interface Event {
        /**
         * @return 返回在app中该view的id
         */
        int id();

        /**
         * listener
         * @return
         */
        Class<?> clazz() default View.OnClickListener.class;
    }
````
