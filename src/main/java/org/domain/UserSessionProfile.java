package org.domain;

import org.apache.hadoop.io.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class UserSessionProfile {
    private String ipAddr;
    private List<Session> sessions;
    private Integer sessionCount;
    private Long avgSessionTime;
    private Long longestDuration;  // Largest session with at least N user interactions

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
        this.sessionCount = sessions.size();
    }

    public Long getLongestDuration() {
        return longestDuration;
    }

    public void setLongestDuration(Long longestDuration) {
        this.longestDuration = longestDuration;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Long getAvgSessionTime() {
        return avgSessionTime;
    }

    public void setAvgSessionTime(Long avgSessionTime) {
        this.avgSessionTime = avgSessionTime;
    }

    // To be used for pretty print
    public Text print() {
        return new Text (
                "UserSessionProfile{" +
                "ipAddr='" + ipAddr + '\'' +
                ", sessions=" + sessions +
                ", sessionCount=" + sessionCount +
                ", avgSessionTime=" + avgSessionTime / 60000 + " min " + (avgSessionTime / 1000) % 60 + " sec" +
                '}');
    }

    public String printCSV() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Session session: sessions) {
          sb.append("[")
                .append(i)
                .append("]")
                .append("(")
                .append(new Date(session.getVisitStart()))
                .append(" ")
                .append(new Date(session.getVisitEnd()))
                .append(" ")
                .append(session.getUniqueUrlCount())
                .append(") ");
            i++;
        }
        return ipAddr +","+ sessionCount +","+ avgSessionTime / 60000 + " min " + (avgSessionTime / 1000) % 60 + " sec" +","+ longestDuration +","+ sb.toString();
    }
}
