package org.domain;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class SessionVisit implements Comparable<SessionVisit> {
    private Long timeStamp;
    private Integer urlHash;

    public SessionVisit(Integer urlHash, Long timeStamp) {
        this.urlHash = urlHash;
        this.timeStamp = timeStamp;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getUrlHash() {
        return urlHash;
    }

    public void setUrlHash(Integer urlHash) {
        this.urlHash = urlHash;
    }

    public int compareTo(SessionVisit o) {
        return new Long(this.timeStamp).compareTo(o.timeStamp);
    }

    @Override
    public String toString() {
        return "SessionVisit{" +
                "timeStamp=" + timeStamp +
                ", urlHash=" + urlHash +
                '}';
    }
}
