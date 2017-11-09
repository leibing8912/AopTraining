## 非侵入式AOP——AspectJ使用

**AspectJ** 是 **Android**平台上一种比较高效和简单的实现编译时AOP技术的方案。

### what is AOP?
* 百度百科定义：在软件业，**AOP**为Aspect Oriented Programming的缩写，意为：**面向切面编程**，通过**预编译方式**和**运行期动态代理**实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

* 简单的来讲，**AOP**是一种：可以在不改变原来代码的基础上，通过**“动态注入”**代码，来改变原来执行结果的技术。

### what can Aspectj do?
* 性能监控: 在方法调用前后记录调用时间，方法执行太长或超时报警。
* 无痕埋点： 在需要埋点的地方添加对应统计代码。
* 缓存代理: 缓存某方法的返回值，下次执行该方法时，直接从缓存里获取。
* 记录日志: 在方法执行前后记录系统日志。
* 权限验证: 方法执行前验证是否有权限执行当前方法，没有则抛出没有权限执行异常，由业务代码捕捉。
* 其他（结合业务扩展）

### Aspectj术语
* **Join Points**
 * 简称**JPoints**，是AspectJ的核心思想之一，它就像一把刀，把程序的整个执行过程切成了一段段不同的部分。例如，构造方法调用、调用方法、方法执行、异常等等，这些都是Join Points，实际上，也就是你想把新的代码插在程序的哪个地方，是插在构造方法中，还是插在某个方法调用前，或者是插在某个方法中，这个地方就是Join Points，当然，不是所有地方都能给你插的，只有**能插**的地方，才叫**Join Points**。
* **Pointcuts**
 * **Pointcuts**，实际上就是在Join Points中通过一定条件选择出我们所需要的Join Points，所以说，Pointcuts，也就是**带条件的Join Points**，作为我们需要的代码切入点。
* **Advice**
 * **Advice**是指具体插入的代码，以及如何插入这些代码。例如Before、After、Around等。

### 引入Aspectj
app/build.grade加入以下配置项：
    
    import org.aspectj.bridge.IMessage
    import org.aspectj.bridge.MessageHandler
    import org.aspectj.tools.ajc.Main
    
    buildscript {
        repositories {
            mavenCentral()
        }
        dependencies {
            classpath 'org.aspectj:aspectjtools:1.8.1'
        }
    }
    
    repositories {
        mavenCentral()
    }
    
    android {
        ...
    }
    
    dependencies {
        ...
        compile 'org.aspectj:aspectjrt:1.8.1'
    }
    
    final def log = project.logger
    final def variants = project.android.applicationVariants
    
    variants.all { variant ->
        if (!variant.buildType.isDebuggable()) {
            log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
            return;
        }
    
        JavaCompile javaCompile = variant.javaCompile
        javaCompile.doLast {
            String[] args = ["-showWeaveInfo",
                         "-1.5",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
            log.debug "ajc args: " + Arrays.toString(args)
    
            MessageHandler handler = new MessageHandler(true);
            new Main().run(args, handler);
            for (IMessage message : handler.getMessages(null, true)) {
               switch (message.getKind()) {
                    case IMessage.ABORT:
                    case IMessage.ERROR:
                    case IMessage.FAIL:
                        log.error message.message, message.thrown
                        break;
                    case IMessage.WARNING:
                        log.warn message.message, message.thrown
                        break;
                    case IMessage.INFO:
                        log.info message.message, message.thrown
                        break;
                    case IMessage.DEBUG:
                        log.debug message.message, message.thrown
                        break;
                }
            }
        }
    }

### AspectJ 语法
JPoint的分类以及对应的Pointcut如下所示：
![](http://emyfs.bs2cdn.yy.com/NDNmZDgxNmYtZTUwYy00OGIzLTg0NDUtNDkwNDEyN2ZhNmQ2.png)

Pointcut中的Signature如下所示：
![](http://emyfs.bs2cdn.yy.com/YTM4M2RiYWEtMGZlMS00NTEzLThlODItM2Y1Y2U1OTE0YzNi.png)

Pointcut中的Signature由一段表达式组成，每个关键词之间都有空格，以下是对关键词的说明：
![](http://emyfs.bs2cdn.yy.com/MGY3NjViMTctYWQyZS00OGViLTg1ZmMtNzBlOGZkNjg1ZTQz.png)

以下是Advice用法说明：
![](http://emyfs.bs2cdn.yy.com/N2VlMzFlNWItY2ZlMC00OWZkLWI4N2EtYWU0ZGEzMGJlNTNi.png)

间接JPoint高级语法，如下所示：
![](http://emyfs.bs2cdn.yy.com/Y2E4Mzc5OWQtNDNhOC00NWUxLWFkMWEtNjYzYjM2Y2I5NDIw.png)

### 使用
首先定义两个Model类，验证结果用，代码如下：

StuModel:



    package com.yy.live.aoptraining.model;
    import android.util.Log;
    
    /**
     * @className: StuModel
     * @classDescription: stu model for aspectj
     * @author: leibing
     * @email: leibing@yy.com
     * @createTime:2017/11/3
     */
    public class StuModel {
    // TAG
    private final static String TAG = "AOP StuModel";
    // stu name
    private String stuName;
    
    static {
    Log.e(TAG, " StuModel static block");
    }
    
    /**
     * construction
     *
     * @param stuName
     */
    public StuModel(String stuName) {
    this.stuName = stuName;
    Log.e(TAG, " StuModel Construction");
    }
    
    /**
     * get stu name
     *
     * @return
     */
    public String getStuName() {
    Log.e(TAG, " get stu name");
    return stuName;
    }
    
    /**
     * set stu name
     *
     * @param stuName
     */
    public void setStuName(String stuName) {
    this.stuName = stuName;
    Log.e(TAG, " set stu name");
    }
    
    /**
     * create throws
     */
    public void createThrows(){
    Log.e(TAG, " createThrows");
    try {
    String a = null;
    a.toString();
    }catch (Exception ex){
    ex.printStackTrace();
    }
    }
    }


UserModel:

    package com.yy.live.aoptraining.model;
    import android.util.Log;
    
    /**
     * @className: UserModel
     * @classDescription: user model for aspectj
     * @author: leibing
     * @email: leibing@yy.com
     * @createTime:2017/11/3
     */
    public class UserModel {
    // TAG
    private final static String TAG = "AOP UserModel";
    // user name
    private String userName;
    
    static {
    Log.e(TAG, " UserModel static block");
    }
    
    /**
     * construction
     *
     * @param userName
     */
    public UserModel(String userName) {
    this.userName = userName;
    Log.e(TAG, " UserModel Construction");
    }
    
    /**
     * get user name
     *
     * @return
     */
    public String getUserName() {
    Log.e(TAG, " get user name");
    return this.userName;
    }
    
    /**
     * set user name
     *
     * @param userName
     */
    public void setUserName(String userName) {
    this.userName = userName;
    Log.e(TAG, " set user name");
    }
    
    /**
     * work
     */
    public void work() {
    Log.e(TAG, " work");
    try {
    Thread.sleep(2000);
    } catch (InterruptedException e) {
    e.printStackTrace();
    }
    new Thread(new Runnable() {
    @Override
    public void run() {
    try {
    Thread.sleep(1000);
    } catch (InterruptedException e) {
    e.printStackTrace();
    }
    }
    }).start();
    }
    
    /**
     * create throws
     */
    public void createThrows(){
    Log.e(TAG, " createThrows");
    try {
    Integer.parseInt("abc");
    }catch (Exception ex){
    ex.printStackTrace();
    }
    }
    }

#### 构造函数
***构造函数被调用***

    /**
     * 构造函数被调用
     */
    @Pointcut("call(com.yy.live.aoptraining.model..*.new(..))")
    public void callConstructor() {
    }
    
    /**
     * 执行(构造函数被调用)JPoint之前
     *
     * @param joinPoint
     */
    @Before("callConstructor()")
    public void beforeConstructorCall(JoinPoint joinPoint) {
    Log.e(TAG, " before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }
    
    /**
     * 执行（构造函数被调用）JPoint之后
     *
     * @param joinPoint
     */
    @After("callConstructor()")
    public void afterConstructorCall(JoinPoint joinPoint) {
    Log.e(TAG, " after->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }

***结果***

    11-09 11:26:39.840 18747-18747/com.yy.live.aoptraining E/AOP ConstructorAspect:  before->com.yy.live.aoptraining.constructor.ConstructorActivity@54b5f45#<init>
    11-09 11:26:39.842 18747-18747/com.yy.live.aoptraining E/AOP UserModel:  UserModel static block
    11-09 11:26:39.842 18747-18747/com.yy.live.aoptraining E/AOP UserModel:  UserModel Construction
    11-09 11:26:39.842 18747-18747/com.yy.live.aoptraining E/AOP ConstructorAspect:  after->com.yy.live.aoptraining.constructor.ConstructorActivity@54b5f45#<init>

从上面结果可以看到在UserModel构造函数之前后分别插入了相关的日志，从而实现了对构造函数被调用AOP处理。

***构造函数执行内部***

    /**
     * 构造函数执行内部
     */
    @Pointcut("execution(com.yy.live.aoptraining.model..*.new(..))")
    public void executionConstructor() {}
    
    /**
     * (构造函数执行内部)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
     * @param joinPoint
     * @throws Throwable
     */
    @Around("executionConstructor()")
    public void aroundConstructorExecution(ProceedingJoinPoint joinPoint) throws Throwable {
    Log.e(TAG, " around->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    // 执行原代码
    joinPoint.proceed();
    }

***结果***
    
    11-09 11:32:38.677 24213-24213/com.yy.live.aoptraining E/AOP UserModel:  UserModel static block
    11-09 11:32:38.678 24213-24213/com.yy.live.aoptraining E/AOP ConstructorAspect:  around->com.yy.live.aoptraining.model.UserModel@379a83e#<init>
    11-09 11:32:38.678 24213-24213/com.yy.live.aoptraining E/AOP UserModel:  UserModel Construction
    11-09 11:32:38.679 24213-24213/com.yy.live.aoptraining E/AOP StuModel:  StuModel static block
    11-09 11:32:38.679 24213-24213/com.yy.live.aoptraining E/AOP ConstructorAspect:  around->com.yy.live.aoptraining.model.StuModel@a03b69f#<init>
    11-09 11:32:38.679 24213-24213/com.yy.live.aoptraining E/AOP StuModel:  StuModel Construction

从上面结果可以看到在UserModel、StuModel构造函数之前分别插入了相关的日志，从而实现了对构造函数执行内部AOP处理,@Around实现了和@Before、@Afrer一样的效果，但是与其不能共用。

#### 属性
此处粗略说下读取属性，代码如下：

    /**
     * 读变量
     */
    @Pointcut("get(String com.yy.live.aoptraining.model.UserModel.userName)")
    public void getField() {
    }
    
    /**
     * (读变量)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("getField()")
    public String aroundFieldGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行原代码
        Object obj = joinPoint.proceed();
        String userName = obj.toString();
        Log.e(TAG, " around->userName = " + userName);
        // 可在此处偷天换日更改类原有属性的值
        return "李四";
    }

***结果***

    11-09 11:46:03.788 3942-3942/com.yy.live.aoptraining E/AOP UserModel:  UserModel static block
    11-09 11:46:03.788 3942-3942/com.yy.live.aoptraining E/AOP UserModel:  UserModel Construction
    11-09 11:46:03.788 3942-3942/com.yy.live.aoptraining E/AOP UserModel:  get user name
    11-09 11:46:03.789 3942-3942/com.yy.live.aoptraining E/AOP FieldAspect:  around->userName = 张三
    11-09 11:46:03.789 3942-3942/com.yy.live.aoptraining E/AOP FieldActivity:  userName = 李四

结果显示已完美动态更改了属性值。

#### 方法
话不多说，show code：

    /**
     * 函数被调用
     */
    @Pointcut("call(* com.yy.live.aoptraining.model.UserModel.**(..))")
    public void callMethod() {
    }

    /**
     * 执行(函数被调用)JPoint之前
     *
     * @param joinPoint
     */
    @Before("callMethod()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, " before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        beforeTarget =  joinPoint.getTarget().toString();
        beforeSignatureName = joinPoint.getSignature().getName();
        beforeTime  = System.currentTimeMillis();
    }

    /**
     * 执行（函数被调用）JPoint之后
     *
     * @param joinPoint
     */
    @After("callMethod()")
    public void afterMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, " after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        afterTarget =  joinPoint.getTarget().toString();
        afterSignatureName = joinPoint.getSignature().getName();
        afterTime = System.currentTimeMillis();
        if (afterTarget != null
                && afterSignatureName != null
                && afterTarget.equals(beforeTarget)
                && afterSignatureName.equals(beforeSignatureName)) {
            long castTime = afterTime - beforeTime;
            Log.e(TAG, " monitor->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName() + "#cost " + castTime + " ms");
        }
    }
    
    /**
     * 替换原方法返回值
     * 注：@Pointcut可以不单独定义方法，直接使用，如下：
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     * @Around("execution(* com.yy.live.aoptraining.model.UserModel.getUserName(..))")
     */
    @Around("execution(* com.yy.live.aoptraining.model.UserModel.getUserName(..))")
    public String aroundGetUserNameMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String originUserName = joinPoint.proceed().toString();
        Log.e(TAG, " origin userName = " + originUserName);
        // 此处可对原方法做偷天换日处理
        return "王五";
    }

***结果***

    11-09 12:02:50.681 18513-18513/com.yy.live.aoptraining E/AOP MethodAspect:  before->com.yy.live.aoptraining.model.UserModel@33363ec#work
    11-09 12:02:50.681 18513-18513/com.yy.live.aoptraining E/AOP UserModel:  work
    11-09 12:02:52.684 18513-18513/com.yy.live.aoptraining E/AOP MethodAspect:  after->com.yy.live.aoptraining.model.UserModel@33363ec#work
    11-09 12:02:52.684 18513-18513/com.yy.live.aoptraining E/AOP MethodAspect:  monitor->com.yy.live.aoptraining.model.UserModel@33363ec#work#cost 2003 ms

结果显示在方法执行前后做了AOP日志插入并统计了该方法在主线程耗时时间。 

#### 异常
AOP的使用场景包括异常处理、统计异常，代码如下：

    /**
     * 异常处理，用于统计所有出现Exception的点
     * 不支持@After、@Around
     */
    @Before("handler(java.lang.Exception)")
    public void handler() {
    Log.e(TAG, " handler");
    }
    
    /**
     * 异常退出，用于收集抛出异常的方法信息
     * @AfterThrowing
     * @param throwable
     */
    @AfterThrowing(pointcut = "call(* *..*(..))", throwing = "throwable")
    public void anyFuncThrows(Throwable throwable) {
    Log.e(TAG, " Throwable: ", throwable);
    }


***结果***

    11-09 12:10:55.419 25880-25880/com.yy.live.aoptraining E/AOP UserModel:  createThrows
    11-09 12:10:55.420 25880-25880/com.yy.live.aoptraining E/AOP MethodAspect:  Throwable: 
       java.lang.NumberFormatException: For input string: "abc"
       at java.lang.Integer.parseInt(Integer.java:521)
       at java.lang.Integer.parseInt(Integer.java:556)
       at com.yy.live.aoptraining.model.UserModel.createThrows(UserModel.java:79)
       at com.yy.live.aoptraining.method.MethodActivity.onCreate(MethodActivity.java:28)
       at android.app.Activity.performCreate(Activity.java:6910)
       at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1123)
       at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2746)
       at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2864)
       at android.app.ActivityThread.-wrap12(ActivityThread.java)
       at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1567)
       at android.os.Handler.dispatchMessage(Handler.java:105)
       at android.os.Looper.loop(Looper.java:156)
       at android.app.ActivityThread.main(ActivityThread.java:6577)
       at java.lang.reflect.Method.invoke(Native Method)
       at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:942)
       at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:832)
    11-09 12:10:55.420 25880-25880/com.yy.live.aoptraining E/AOP MethodAspect:  handler

通过AOP可以统计对应的异常情况并且将对应的异常放到一个统一的地方集中处理。

#### 权限验证
此处用一个6.0版本以上动态权限申请作为示例，首先写一个动态权限注解接口，代码如下：

    package com.yy.live.aoptraining.permission;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    /**
     * @className: YPermission
     * @classDescription: 动态权限申请注解
     * @author: leibing
     * @email: leibing@yy.com
     * @createTime:2017/11/3
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface YPermission {
    String value();
    }

接着写对应的Aspect，代码如下：

    package com.yy.live.aoptraining.permission;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.util.Log;
    import com.yy.live.aoptraining.AppManager;
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Pointcut;
    
    /**
     * @className: PermissionAspect
     * @classDescription: permission aspectj
     * @author: leibing
     * @email: leibing@yy.com
     * @createTime:2017/11/3
     */
    @Aspect
    public class PermissionAspect {
    // TAG
    private final static String TAG = "AOP PermissionAspect";
    
    /**
     * 函数执行内部（采用注解动态权限处理）
     *
     * @param permission
     */
    @Pointcut("execution(@com.yy.live.aoptraining.permission.YPermission * *(..)) && @annotation(permission)")
    public void methodAnnotatedWithMPermission(YPermission permission) {
    }
    
    /**
     * (函数执行内部（采用注解动态权限处理）)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
     *
     * @param joinPoint
     * @param permission
     * @throws Throwable
     */
    @Around("methodAnnotatedWithMPermission(permission)")
    public void checkPermission(final ProceedingJoinPoint joinPoint, YPermission permission) throws Throwable {
    Log.e(TAG, " checkPermission");
    // 权限
    String permissionStr = permission.value();
    // 模拟权限申请
    if (AppManager.getInstance().currentActivity() != null) {
    new AlertDialog.Builder(AppManager.getInstance().currentActivity()).setTitle("提示")
    .setMessage(permissionStr)
    .setNegativeButton("取消", null)
    .setPositiveButton("允许", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    Log.e(TAG, " checkPermission allow");
    try {
    // 继续执行原方法
    joinPoint.proceed();
    } catch (Throwable throwable) {
    throwable.printStackTrace();
    }
    
    }
    }).create().show();
    }
    }
    }

代码注释也比较详细，不细讲了。

#### 总结
本文粗略的描述了Aspectj一些基本的用法、应用场景并做了示例分析。一说起AOP，大家想到的就是埋点、埋点、还是埋点，埋点只是AOP最基础的功能罢了，还有很多更高级的用法：性能监控、权限验证、数据校验、缓存、其他（项目中特别的一些需求）。目前我就遇到两个问题，就是接收到广播出现多次重复的问题，于是就想办法去过滤，于是就想到了用Handler做延迟处理，效果不太理想，虽然解决了一时的问题，并且还有个问题就是每个广播接收器处都要写一遍，代码有点冗余，如果此处采用AOP就非常简单了，写一个注解，采用类似动态权限申请的方式去做一个统一处理就较完美的解决了这个问题，以后维护起来也很方便；另外一个是点击按钮的时候，有时会多次触发事件，这种情况会引发并发执行的相关bug，于是就是在点击的时候设置按钮为不可点击，逻辑处理完再设置为可点击，然后每个这样的事件都要写一遍，若采用AOP，则全部集中处理了。类似的问题，应该还有挺多的。也许大家在担心采用Aspectj会带来相关问题：性能问题？这个不用担心，Aspectj是属于编译时的，不会对app性能造成影响；增加apk包大小？
反编译任意主流apk去看，apk包中代码永远是占据小部分大小，资源 + so包等才是重心，去查看了Aspectj编译时插入的代码（4KB）占apk大小(1.5MB)，几乎微乎其微，基本没影响；插件不支持multiple dex,插件方法数超65535？反编译查看代码发现使用Aspectj切入的页面，只生成了一个ajc$preClinit初始化切点的方法，这对插件方法数的影响微乎其微。综上述得之，使用Aspectj会带来更多的便捷，提高工作效率，降低维护成本。

[AspectJ Demo 源码链接](https://github.com/leibing8912/AopTraining "AspectJ Demo 源码链接")


License

Copyright 2017 leibing

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.