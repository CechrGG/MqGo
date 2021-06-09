# RocketMq
## 架构

## 编译安装
* git
> git@github.com:apache/rocketmq.git
* 导入工程之后，maven goal
```shell
mvn -Prelease-all -DskipTests clean install -U 
```
> 注意是在父工程目录下执行 {源码}/rocketmq
* distribution的target目录下编译生成工程文件包rocketmq-{version}.tar.gz  
> 拷贝到服务器/opt/下并解压
```shell
tar -zxvf rocketmq-{version}.tar.gz  
```
* 启动nameserver， 先cd /opt/rocketmq-{version}/bin
> vim runserver.sh 及 runbroker.sh 可修改jvm参数，默认都比较大 
```shell
nohup sh mqnamesrv & 
```
> 这里可以查看默认日志，cat nohup.out , 如果有“mqnamesrv:行2: $'\r': 未找到命令”错误是因为windows编译文件造成的        
> 可以通过dos2unix工具进行转换  
```shell
yum install dos2unix      
dos2unix mqnamesrv    //所有需要转换的脚本逐一转换        
find /opt/rocketmq/bin -name "*.sh" -exec dos2unix {} \;  //批量转换
```
> 验证是否成功可通过jps命令查看进程，正常：
```shell
> jps
47936 NamesrvStartup
```
> 查看日志，有 
```shell
The Name Server boot success. serializeType=JSON 
```      
> 说明启动成功, 默认端口是9876
* 启动broker 
> 修改conf/broker.conf，添加brokerIP1=192.168.28.83
```shell
nohup sh bin/mqbroker -n 192.168.28.83:9876 -c conf/broker.conf &
```
> jps命令查看进程，有
```shell
48547 BrokerStartup
```
> 查看日志，有
```shell
The broker[broker-a, 192.168.28.83:10911] boot success. serializeType=JSON and name server is 192.168.28.83:9876
```
