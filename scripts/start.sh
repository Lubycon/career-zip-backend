#!/bin/bash

REPOSITORY=/home/ubuntu/app/career-zip
APPLICATION_PROPERTIES=/home/ubuntu/app/
PROJECT_NAME=career-zip

echo "> Build 파일 복사"

cp $REPOSITORY/build/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl career-zip | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
  echo "애플리케이션 종료 확인 pid : $CURRENT_PID"
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

echo "> $JAR_NAME에 실행 권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
  -Dspring.config.location=$APPLICATION_PROPERTIES \
  -Dspring.profiles.active=prod \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
