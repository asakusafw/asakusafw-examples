### example-summarization building and execution

    mvn assembly:single antrun:run
    mvn clean package eclipse:eclipse
    cp target/*batchapps*.jar $ASAKUSA_HOME/batchapps
    cd $ASAKUSA_HOME/batchapps
    jar xf *batchapps*.jar

    $ASAKUSA_HOME/yaess/bin/yaess-batch.sh example.summarization -A FROM_ISSUE_DATE=20120101 -A TO_ISSUE_DATE=20120131
