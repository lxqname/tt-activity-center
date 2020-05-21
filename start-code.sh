#!/bin/bash
cd `dirname $0`

# 环境参数
inv_pre=$1

# 当前主机名
host_name=`hostname`

if [ "$inv_pre" = "dev" ] && [ $host_name != "VM_0_5_centos" ]; then
	echo "Illegal environment and host name!"
	echo "当前主机名不是开发环境！请确认环境及命令。"
	exit -1
elif [ "$inv_pre" = "uat" ] && [ $host_name != "VM_0_6_centos" ]; then
	echo "Illegal environment and host name!"
	echo "当前主机名不是测试环境！请确认环境及命令。"
	exit -1

fi

# skywalking的 ip及端口
skywalking_addr=""

# skywalking的应用目录
skywalking_dir=""

# zookeeper的 ip及端口
zookeeper_addr=""

# 开发环境
if [ "$inv_pre" = "dev" ]; then
	skywalking_addr="10.16.0.17:11800"
	skywalking_dir="/root/skywalking"
	zookeeper_addr="10.0.0.17:2181"
# 测试环境
elif [ "$inv_pre" = "uat" ]; then
	skywalking_addr="10.16.0.5:11800"
	skywalking_dir="/home/skywalking"
	zookeeper_addr="10.0.0.5:2181"
# 生产环境
elif [ "$inv_pre" = "prd" ]; then
	echo "Product environment preparation is going."
	echo "生产环境还在搭建中。。。"
	exit -1
# 环境参数错误
else
	echo "Illegal shell param for environment as dev, uat, or prd!"
	echo "运行命令缺少环境参数，请补充完整，如：sh startup.sh dev"
	exit -1
fi

img_mvn="maven:3.3.3-jdk-8"                 # docker image of maven
m2_cache=~/.m2                              # the local maven cache dir
proj_home=$PWD                              # the project root dir
img_output="deepexi/tt-activity-center"         # output image tag

git pull  # should use git clone https://name:pwd@xxx.git

echo "use docker maven"
docker run --rm \
   -v $m2_cache:/root/.m2 \
   -v $proj_home:/usr/src/mymaven \
   -w /usr/src/mymaven $img_mvn mvn clean package -U

sudo mv $proj_home/tt-activity-center-provider/target/tt-activity-center-provider-*.jar $proj_home/tt-activity-center-provider/target/demo.jar # 兼容所有sh脚本
docker build -t $img_output .

mkdir -p $PWD/logs
chmod 777 $PWD/logs

# 删除容器
docker rm -f tt-activity-center &> /dev/null

version=`date "+%Y%m%d%H"`

# 启动镜像
docker run -d --restart=on-failure:5 --privileged=true \
    --net=host \
    --dns 114.114.114.114 \
    --env 'TZ=Asia/Shanghai' \
    -w /home \
    -v /usr/share/zoneinfo/Asia/Shanghai:/etc/localtime:ro \
    -v $PWD/logs:/home/logs \
    -v ${skywalking_dir}/agent:/home/agent \
    -e SW_AGENT_NAME=tt-activity-center \
    -e SW_AGENT_COLLECTOR_BACKEND_SERVICES=${skywalking_addr} \
    --name tt-activity-center deepexi/tt-activity-center \
    java \
        -Xmx200m \
        -Xms200m \
        -Djava.security.egd=file:/dev/./urandom \
        -Duser.timezone=Asia/Shanghai \
        -XX:+PrintGCDateStamps \
        -XX:+PrintGCTimeStamps \
        -XX:+PrintGCDetails \
        -XX:+UseG1GC \
        -XX:+HeapDumpOnOutOfMemoryError \
        -Xloggc:logs/gc_$version.log \
        -javaagent:/home/agent/skywalking-agent.jar  \
        -jar /home/demo.jar \
        --spring.profiles.active=${inv_pre} \
        --dubbo.registry.address=zookeeper://${zookeeper_addr} \
        --elasticJob.regCenter.serverList=${zookeeper_addr}