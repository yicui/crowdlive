// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.ui;

import edu.vanderbilt.drumbeat.ui.LineDrawer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect LineDrawer_Roo_Equals {
    
    public boolean LineDrawer.equals(Object obj) {
        if (!(obj instanceof LineDrawer)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LineDrawer rhs = (LineDrawer) obj;
        return new EqualsBuilder().append(canvas, rhs.canvas).isEquals();
    }
    
    public int LineDrawer.hashCode() {
        return new HashCodeBuilder().append(canvas).toHashCode();
    }
    
}
