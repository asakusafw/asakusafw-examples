# Asakusa Framework Application Example: Summarize Sales

## Install Framework on Development Environment
```
./gradlew installAsakusafw
```

## Build
```
./gradlew build
```

## Deploy
```
cp build/*batchapps*.jar $ASAKUSA_HOME/batchapps
cd $ASAKUSA_HOME/batchapps
jar xf *batchapps*.jar
```

## Run
```
$ASAKUSA_HOME/yaess/bin/yaess-batch.sh example.summarization -A FROM_ISSUE_DATE=20120101 -A TO_ISSUE_DATE=20120131
```

