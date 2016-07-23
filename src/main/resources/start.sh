#!/bin/bash
hadoop jar ./wesessionizer-1.0-SNAPSHOT.jar $1 $2
pig -x mapreduce -param out=$2 script.pig