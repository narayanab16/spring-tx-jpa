# spring-tx-jpa

-  Prereq: Java 18 or Java 20, IntelliJ
-  Please create a dir in D:/test/h2db or you /path/to/folder
-  RunDBServer.java uses H2 in server mode
-  DB access url: http://192.168.56.1:8082
-  JVM options must : --add-opens java.base/java.lang=ALL-UNNAMED
-  RdbmsApplication.java start the rest app
-  Test concurrency example of the same account with withdraw money
-  Concurrency Test: http://localhost:10000/callMultiThread
-  You will see only 1 of the transaction successful 2 fails on the same account
- # Happy Learning simple db srver and effective spring tx 