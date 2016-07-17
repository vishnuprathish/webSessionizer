package org.domain;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class SessionVisitWritable implements Writable {
    private LongWritable timeStamp;
    private IntWritable urlHash;

    public SessionVisitWritable() {
        timeStamp = new LongWritable();
        urlHash = new IntWritable();
    }

    public SessionVisitWritable(IntWritable urlHash, LongWritable timeStamp) {
        this.urlHash = urlHash;
        this.timeStamp = timeStamp;
    }

    public void write(DataOutput dataOutput) throws IOException {
        timeStamp.write(dataOutput);
        urlHash.write(dataOutput);
    }

    public void readFields(DataInput dataInput) throws IOException {
        timeStamp.readFields(dataInput);
        urlHash.readFields(dataInput);
    }

    public LongWritable getTimeStamp() {
        return timeStamp;
    }

    public IntWritable getUrlHash() {
        return urlHash;
    }
}
