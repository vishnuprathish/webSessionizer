package org.domain;

import org.apache.commons.net.ntp.TimeStamp;

import java.util.Date;

/**
 * Created by vishnu.prathish on 7/16/2016.
 */
public class Session {
    private Long visitStart;
    private Long visitEnd;
    private Long duration;

    public Session(Long visitStart, Long visitEnd) {
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
        duration = visitEnd - visitStart;
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

    @Override
    public String toString() {
        return "(" + new Date(visitStart) +
                " - " + new Date(visitEnd) +
                ")";
    }
}
