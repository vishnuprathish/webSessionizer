package org.reducer;

import org.domain.Session;
import org.domain.SessionVisitWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.domain.UserSessionProfile;

import java.io.IOException;
import java.util.*;


/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class JobReducer extends Reducer<Text, SessionVisitWritable, Text, Text> {

    public JobReducer() {
        super();
    }

    public List<Session> sessionize(List<Long> sortedTimestamps, Long interval) {
        int i = -1;
        List<Session> sessions = new ArrayList<Session>();
        long sessionStart = sortedTimestamps.get(0);

        // Only one visit. set sessionStart and sessionEnd to same
        if(sortedTimestamps.size() == 1) {
            Session session = new Session(sessionStart, sessionStart);
            sessions.add(session);
            return sessions;
        }

        while(i < sortedTimestamps.size() - 2) {
            long t1 = sortedTimestamps.get(++i);
            long t2 = sortedTimestamps.get(i+1);
            if ((t2 - t1) < interval) {
                continue;
            } else {
                Session session = new Session(sessionStart, t1);
                sessions.add(session);
                sessionStart = t2;
            }
        }
        Session session = new Session(sessionStart, sortedTimestamps.get(i+1));
        sessions.add(session);
        return sessions;
    }

    @Override
    protected void reduce(Text ip, Iterable<SessionVisitWritable> visitList, Context context) throws IOException, InterruptedException {
        List<Long> timeStamps = new ArrayList();
        Set<Integer> uniqueUrls = new HashSet();

        UserSessionProfile sessionProfile = new UserSessionProfile();
        final long INTERVAL = 600000l;

        for(SessionVisitWritable visit : visitList) {
            timeStamps.add(visit.getTimeStamp().get());
            uniqueUrls.add(visit.getUrlHash().get()); // Hashset for duplicate elimination
        }

        // set ip
        sessionProfile.setIpAddr(ip.toString());

        // separate sessions from timestamp
        Collections.sort(timeStamps);
        List<Session> sessions = sessionize(timeStamps, INTERVAL);

        // set sessions
        sessionProfile.setSessions(sessions);

        // set average duration
        long runningSum = 0;
        for(Session session : sessions) {
            runningSum += session.getDuration();
        }
        sessionProfile.setAvgSessionTime(runningSum / sessions.size());

        // set unique visits
        sessionProfile.setUniqueVisits(uniqueUrls.size());

        context.write(ip, sessionProfile.print());

    }
}
