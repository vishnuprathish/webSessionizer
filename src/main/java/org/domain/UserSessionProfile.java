package org.domain;

import org.apache.hadoop.io.Text;

import java.util.List;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class UserSessionProfile {
    private String ipAddr;
    private List<Session> sessions;
    private Integer uniqueVisits;
    private Long avgSessionTime;

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
    }

    public Integer getUniqueVisits() {
        return uniqueVisits;
    }

    public void setUniqueVisits(Integer uniqueVisits) {
        this.uniqueVisits = uniqueVisits;
    }

    public Long getAvgSessionTime() {
        return avgSessionTime;
    }

    public void setAvgSessionTime(Long avgSessionTime) {
        this.avgSessionTime = avgSessionTime;
    }

    public Text print() {
        return new Text("{" +
                "User IP:'" + ipAddr + '\'' +
                ", sessions :" + sessions +
                ", Total Unique Visits :'" + uniqueVisits + '\'' +
                ", Average Session Time :" + avgSessionTime / 60 + " min " + avgSessionTime % 60 + " sec" +
                '}');
    }

}
