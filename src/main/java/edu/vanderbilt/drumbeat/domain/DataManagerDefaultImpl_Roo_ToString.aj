// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.domain;

import edu.vanderbilt.drumbeat.domain.DataManagerDefaultImpl;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

privileged aspect DataManagerDefaultImpl_Roo_ToString {
    
    public String DataManagerDefaultImpl.toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}