@REM   Copyright 2018 Otavio Rodolfo Piske
@REM
@REM   Licensed under the Apache License, Version 2.0 (the "License");
@REM   you may not use this file except in compliance with the License.
@REM   You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM   Unless required by applicable law or agreed to in writing, software
@REM   distributed under the License is distributed on an "AS IS" BASIS,
@REM   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM   See the License for the specific language governing permissions and
@REM   limitations under the License.

@echo off

set QDP_HOME=%~dp0\..

@REM Set 
if %OS%=="Windows_NT" @setlocal
if %OS%=="WINNT" @setlocal

for %%i in ("%QDP_HOME%"\bin\quiver-*) do set QDP_JAR="%%i"

@java -classpath %QDP_JAR% net.orpiske.qdp.main.Main %*