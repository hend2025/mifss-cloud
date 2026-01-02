---
description: 运行Maven命令使用自定义settings.xml
---
// turbo-all

所有Maven命令都应使用 -s 参数指定settings.xml文件：

示例：
```powershell
mvn compile -s "D:\your\path\settings.xml"
mvn package -s "D:\your\path\settings.xml"  
mvn install -s "D:\your\path\settings.xml"
```

请将上面的路径替换为你实际的 settings.xml 路径。
