// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.ui;

import edu.vanderbilt.drumbeat.ui.DataVisualizer;
import edu.vanderbilt.drumbeat.ui.DefaultView;
import edu.vanderbilt.drumbeat.ui.Drawer;

privileged aspect DefaultView_Roo_JavaBean {
    
    public int DefaultView.getCellHeight() {
        return this.cellHeight;
    }
    
    public void DefaultView.setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }
    
    public Drawer DefaultView.getDrawer() {
        return this.drawer;
    }
    
    public void DefaultView.setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }
    
    public DataVisualizer DefaultView.getDataVisualizer() {
        return this.dataVisualizer;
    }
    
    public void DefaultView.setDataVisualizer(DataVisualizer dataVisualizer) {
        this.dataVisualizer = dataVisualizer;
    }
    
}