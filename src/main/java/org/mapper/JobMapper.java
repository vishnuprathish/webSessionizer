package org.mapper;


import org.domain.SessionVisitWritable;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class JobMapper extends Mapper<LongWritable, Text, Text, SessionVisitWritable> {

    /**
     * Obtains userIp from logline
     */
    private Text getUserIp(String[] logLineTokens) throws Exception {
        String s = logLineTokens[2].split(":")[0];
        return new Text(s);
    }

    /**
     * Obtains timestamp from the log line
     */
    private LongWritable getTimeStamp(String[] logLineTokens) throws Exception {
        Calendar dateTime = DatatypeConverter.parseDateTime(logLineTokens[0]);
        return new LongWritable(dateTime.getTimeInMillis());
    }

    /**
     * Only hashcode is used as that's all we need. Lowers network usage
     */
    private IntWritable getUrlHash(String[] logLine) throws Exception {
        String url = logLine[12];
        return new IntWritable(url.hashCode());
    }

    /**
     * Main map function. All sub routine exceptions are caught and ignored
     * because of likely bad data.
     */

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String logLine = value.toString();
        if(StringUtils.isNotEmpty(logLine)) {
            String[] tokens = logLine.split(" ");
            try {

                SessionVisitWritable sessionVisitWritable  = new SessionVisitWritable(
                        getUrlHash(tokens),
                        getTimeStamp(tokens));

                context.write(getUserIp(tokens), sessionVisitWritable);
            } catch (Exception e) {
                // Ignoring invalid data
                // TODO : write line to log
            }
        }

    }
}
