// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.algo;

import edu.vanderbilt.drumbeat.algo.RaderFFTFilter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect RaderFFTFilter_Roo_Equals {
    
    public boolean RaderFFTFilter.equals(Object obj) {
        if (!(obj instanceof RaderFFTFilter)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RaderFFTFilter rhs = (RaderFFTFilter) obj;
        return new EqualsBuilder().append(MINY, rhs.MINY).append(framesize_logarithm, rhs.framesize_logarithm).isEquals();
    }
    
    public int RaderFFTFilter.hashCode() {
        return new HashCodeBuilder().append(MINY).append(framesize_logarithm).toHashCode();
    }
    
}
