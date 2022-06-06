# OpenSrcSW

컴파일

javac -cp jars/jsoup-1.14.3.jar:jars/org.snu.ids.ha.jar src/scripts/*.java -d bin -encoding UTF8

2주차 실행:

java -cp jars/jsoup-1.14.3.jar:jars/org.snu.ids.ha.jar:bin scripts.kuir -c ./data

3주차 실행:

java -cp jars/jsoup-1.14.3.jar:jars/org.snu.ids.ha.jar:bin scripts.kuir -k ./collection.xml

4주차 실행:

java -cp jars/jsoup-1.14.3.jar:jars/org.snu.ids.ha.jar:bin scripts.kuir -i ./index.xml

5주차 실행:

java -cp jars/jsoup-1.14.3.jar:jars/org.snu.ids.ha.jar:bin scripts.kuir -s ./index.post -q "질문"


15주차 컴파일
javac -cp jars/json-simple-1.1.1.jar week15.java

15주차 실행
java -cp jars/json-simple-1.1.1.jar:bin week15.java
