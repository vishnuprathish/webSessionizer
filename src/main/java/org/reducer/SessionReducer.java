package org.reducer;

import org.apache.hadoop.io.Text;
import org.domain.Session;
import org.domain.SessionVisit;
import org.domain.SessionVisitWritable;
import org.domain.UserSessionProfile;

import java.io.IOException;
import java.util.*;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class SessionReducer extends org.apache.hadoop.mapreduce.Reducer<Text, SessionVisitWritable, Text, Text> {

    public SessionReducer() {
        super();
    }

    public List<Session> convertToSessions(List<SessionVisit> visits, Long interval) {
        Collections.sort(visits);
        HashSet<Integer> uniqueUrls = new HashSet<Integer>();

        List<Session> sessions = new ArrayList<Session>();
        long sessionStart = visits.get(0).getTimeStamp();

        // Only one visit. set sessionStart and sessionEnd to same
        if(visits.size() == 1) {
            Session session = new Session(sessionStart, sessionStart, 1);
            sessions.add(session);
            return sessions;
        }

        int i = -1;
        while(i < visits.size() - 2) {
            long t1 = visits.get(++i).getTimeStamp();
            uniqueUrls.add(visits.get(i).getUrlHash());
            long t2 = visits.get(i+1).getTimeStamp();
            if ((t2 - t1) < interval) {
                continue;
            } else {
                Session session = new Session(sessionStart, t1, uniqueUrls.size());
                sessions.add(session);
                uniqueUrls.clear();
                sessionStart = t2;
            }
        }
        Session session = new Session(sessionStart, visits.get(i+1).getTimeStamp(), uniqueUrls.size());
        sessions.add(session);
        return sessions;
    }

    @Override
    protected void reduce(Text ip, Iterable<SessionVisitWritable> visitList, Context context) throws IOException, InterruptedException {

        UserSessionProfile sessionProfile = new UserSessionProfile();
        final long INTERVAL = 600000l; //10 min TODO: Make it configurable

        // set ip
        sessionProfile.setIpAddr(ip.toString());

        List<SessionVisit> visits = new ArrayList<SessionVisit>();
        for(SessionVisitWritable v: visitList) {
            visits.add(new SessionVisit(v.getUrlHash().get(), v.getTimeStamp().get()));
        }

        // separate sessions and unique urls from timestamps
        List<Session> sessions = convertToSessions(visits, INTERVAL);

        // set sessions
        sessionProfile.setSessions(sessions);

        // set average duration
        int runningSum = 0;
        for(Session session : sessions) {
            runningSum += session.getDuration();
        }
        sessionProfile.setAvgSessionTime((long)runningSum / sessions.size());

        // Set longest session.
        // Given data can be used to caculate a better engagement factor though, counting more than longest session
        long longestDuration = 0;
        for(Session session: sessions) {
            longestDuration = session.getDuration() > longestDuration ? session.getDuration() : longestDuration;
        }

        sessionProfile.setLongestDuration(longestDuration);
        context.write(ip, new Text(sessionProfile.printCSV()));

    }
}
