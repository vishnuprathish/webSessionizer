package org.domain;

import java.util.Date;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class Session {
    private Long visitStart;
    private Long visitEnd;
    private Long duration;
    private Integer uniqueUrlCount;

    public Session(Long visitStart, Long visitEnd, Integer uniqueUrlCount) {
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
        duration = visitEnd - visitStart;
        this.uniqueUrlCount = uniqueUrlCount;
    }

    public Long getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(Long visitStart) {
        this.visitStart = visitStart;
    }

    public Long getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(Long visitEnd) {
        this.visitEnd = visitEnd;
    }

    public Long getDuration() {
        return duration;
    }

    public Integer getUniqueUrlCount() {
        return uniqueUrlCount;
    }

    public void setUniqueUrlCount(Integer uniqueUrlCount) {
        this.uniqueUrlCount = uniqueUrlCount;
    }

    @Override
    public String toString() {
        return "(" + new Date(visitStart) +
                " - " + new Date(visitEnd) + ")" +
                " Url Count: " + uniqueUrlCount;
    }
}
