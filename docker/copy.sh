#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20250523.sql ./mysql/db
cp ../sql/ry_config_20250902.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../fly-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy fly-gateway "
cp ../fly-gateway/target/fly-gateway.jar ./ruoyi/gateway/jar

echo "begin copy fly-auth "
cp ../fly-auth/target/fly-auth.jar ./ruoyi/auth/jar

echo "begin copy fly-visual "
cp ../fly-visual/fly-monitor/target/fly-visual-monitor.jar  ./ruoyi/visual/monitor/jar

echo "begin copy fly-modules-system "
cp ../fly-modules/fly-system/target/fly-modules-system.jar ./ruoyi/modules/system/jar

echo "begin copy fly-modules-file "
cp ../fly-modules/fly-file/target/fly-modules-file.jar ./ruoyi/modules/file/jar

echo "begin copy fly-modules-job "
cp ../fly-modules/fly-job/target/fly-modules-job.jar ./ruoyi/modules/job/jar

echo "begin copy fly-modules-gen "
cp ../fly-modules/fly-gen/target/fly-modules-gen.jar ./ruoyi/modules/gen/jar

