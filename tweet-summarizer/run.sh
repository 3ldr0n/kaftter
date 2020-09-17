#!/usr/bin/env bash

$SPARK_HOME/bin/spark-submit \
  --class "kaftter.summarizer.TweetSummarizer" \
  --master local \
  target/scala-2.12/tweet-summarizer_2.12-1.0.jar
