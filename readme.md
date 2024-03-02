我的环境是SpringBoot 2.5.15

另外我为了偷懒，用的是Hutool的HttpUtil工具，所以如果你需要替换，请将 service/SmsbaoSmsImpl.java中对应的代码修改即可

另外我的项目结构路径如下

```
.
├── pom.xml
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── ruoyi
│       │           └── sms
│       │               ├── config
│       │               ├── constants
│       │               ├── controller
│       │               ├── domain
│       │               ├── enums
│       │               ├── mapper
│       │               ├── supplier
│       │               │   └── smsbao
│       │               │       ├── config
│       │               │       │   ├── SmsbaoConfig.java
│       │               │       │   ├── SmsbaoConstant.java
│       │               │       │   └── SmsbaoFactory.java
│       │               │       └── service
│       │               │           └── SmsbaoSmsImpl.java
```

所以对应的包文件请根据自己的需求调整，代码参考
