@echo off


set CURRENT_DIR=%cd%
cd %CURRENT_DIR%

set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/classes12.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/commons-beanutils.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/commons-logging.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/dom4j-1.6.1.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/jade-tool2.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/jade-tool2-src.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/velocity-1.7.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/libs/velocity-1.7-dep.jar
set LIBS_PATH=%LIBS_PATH%;%CURRENT_DIR%/common-config.properties

set CLASSPATH=%LIBS_PATH%;

java -classpath "%CLASSPATH%" com.qieren.tool.core.Generate