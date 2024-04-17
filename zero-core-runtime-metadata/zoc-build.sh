#!/usr/bin/env bash
# 检查环境变量 ZERO_HOME
if [ -z "$ZERO_HOME" ]; then
  echo "ZERO_HOME 环境变量未设置！"
  exit 1
fi
mvn clean package install -Dquickly -DskipTests=true -Dmaven.javadoc.skip=true -Dmaven.compile.fork=true -T 1C