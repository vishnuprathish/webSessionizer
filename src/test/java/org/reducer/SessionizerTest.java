package org.reducer;

import org.domain.Session;
import org.domain.SessionVisit;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class SessionizerTest {

    SessionReducer sessionReducer = new SessionReducer();
    private static Long INTERVAL = 600000l; //ten minutes in millis

    private Long getTimeStamp(String timeStamp) throws Exception {
        Calendar dateTime = DatatypeConverter.parseDateTime(timeStamp);
        return dateTime.getTimeInMillis();
    }

    @Test
    public void test() {

        Integer URL_HASH1 = 1;

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

        List<SessionVisit> visits = new ArrayList<SessionVisit>();

        for(String timeStamp : timeStamps) {
            try {
                SessionVisit sessionVisit = new SessionVisit(URL_HASH1, getTimeStamp(timeStamp));
                visits.add(sessionVisit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Session> sessions = sessionReducer.convertToSessions(visits, INTERVAL);
        Assert.assertEquals(3, sessions.size());

    }


    /**
     *     Contains unsorted timestamps. A sort is performed internally.
     *     Should still contain 3 sessions as dataset is basically same with
     *     duplicates
      */

    @Test
    public void testUnsorted() {

        Integer URL_HASH1 = 1;
        Integer URL_HASH3 = 3;
        List<Integer> urlhashs = new ArrayList<Integer>();
        urlhashs.add(URL_HASH1);
        urlhashs.add(URL_HASH3);

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

        List<SessionVisit> visitsUnsorted = new ArrayList<SessionVisit>();

        int i =0;
        for(String timeStamp : timeStamps) {
            i++;
            try {
                SessionVisit sessionVisit = new SessionVisit(urlhashs.get(i % 2), getTimeStamp(timeStamp));
                visitsUnsorted.add(sessionVisit);
            } catch (Exception e) { e.printStackTrace(); }
        }

        List<Session> sessions = sessionReducer.convertToSessions(visitsUnsorted, INTERVAL);
        Assert.assertEquals(3, sessions.size());
        Integer TWO = 2;
        Assert.assertEquals(sessions.get(0).getUniqueUrlCount(), TWO);

    }
}
