#!/usr/bin/env bash
./zoc-build.sh
echo "Zero OSGI Development..."
cp -rf target/*.jar $ZERO_HOME/develop/libs/bundles/