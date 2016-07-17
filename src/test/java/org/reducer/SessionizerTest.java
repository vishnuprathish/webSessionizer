package org.reducer;

import org.domain.Session;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class SessionizerTest {

    JobReducer reducer = new JobReducer();
    private static Long INTERVAL = 600000l; //ten minutes in millis

    private Long getTimeStamp(String timeStamp) throws Exception {
        Calendar dateTime = DatatypeConverter.parseDateTime(timeStamp);
        return dateTime.getTimeInMillis();
    }

    @Test
    public void test() {

        String[] timeStamps = {
                "2015-07-22T09:00:28.348141Z",
                "2015-07-22T09:01:28.348141Z",
                "2015-07-22T09:01:28.348141Z",
                "2015-07-22T09:11:29.348141Z", // session switch
                "2015-07-22T09:11:30.348141Z",
                "2015-07-22T09:25:28.348141Z", // session switch
                "2015-07-22T09:25:47.348141Z",
                "2015-07-22T09:25:49.348141Z",
        };

        List<Long> sortedTimestamps = new ArrayList<Long>();

        for(String timeStamp : timeStamps) {
            try {
                sortedTimestamps.add(getTimeStamp(timeStamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Session> sessions = reducer.sessionize(sortedTimestamps, INTERVAL);
        Assert.assertEquals(3, sessions.size());

    }


    /**
     *     Contains unsorted timestamps. A sort is performed before sessionizing.
     *     Should still contain 3 sessions as dataset is basically same with
     *     duplicates
      */

    @Test
    public void testUnsorted() {

        String[] timeStamps = {
                "2015-07-22T09:01:28.348141Z",
                "2015-07-22T09:00:28.348141Z",
                "2015-07-22T09:01:28.348141Z",
                "2015-07-22T09:11:30.348141Z",
                "2015-07-22T09:11:29.348141Z",
                "2015-07-22T09:25:49.348141Z",
                "2015-07-22T09:25:49.348141Z",
                "2015-07-22T09:11:30.348141Z",
                "2015-07-22T09:11:30.348141Z",
                "2015-07-22T09:11:30.348141Z",
                "2015-07-22T09:25:28.348141Z",
                "2015-07-22T09:25:47.348141Z",
                "2015-07-22T09:11:30.348141Z",
                "2015-07-22T09:11:30.348141Z",

        };

        List<Long> timestamps = new ArrayList<Long>();

        for(String timeStamp : timeStamps) {
            try {
                timestamps.add(getTimeStamp(timeStamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Sort before sessionizing
        Collections.sort(timestamps);

        List<Session> sessions = reducer.sessionize(timestamps, INTERVAL);
        Assert.assertEquals(3, sessions.size());

    }
}
