#!/usr/bin/env bash
./zoc-build.sh
echo "Zero OSGI Dispatch..."
if [ "core" == "$1" ]; then
  cp -rf target/*.jar $ZERO_HOME/bundles/infix
elif [ "extension" == "$1" ]; then
  cp -rf target/*.jar $ZERO_HOME/bundles/extension
else
  cp -rf target/*.jar $ZERO_HOME/bundles/core
fi