// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.algo;

import edu.vanderbilt.drumbeat.algo.CooleyTukeyFFTFilter;

privileged aspect CooleyTukeyFFTFilter_Roo_JavaBean {
    
    public short[] CooleyTukeyFFTFilter.getWeightingWindow() {
        return this.weightingWindow;
    }
    
    public void CooleyTukeyFFTFilter.setWeightingWindow(short[] weightingWindow) {
        this.weightingWindow = weightingWindow;
    }
    
    public int[] CooleyTukeyFFTFilter.getFftBuffer_real() {
        return this.fftBuffer_real;
    }
    
    public void CooleyTukeyFFTFilter.setFftBuffer_real(int[] fftBuffer_real) {
        this.fftBuffer_real = fftBuffer_real;
    }
    
    public int[] CooleyTukeyFFTFilter.getFftBuffer_imag() {
        return this.fftBuffer_imag;
    }
    
    public void CooleyTukeyFFTFilter.setFftBuffer_imag(int[] fftBuffer_imag) {
        this.fftBuffer_imag = fftBuffer_imag;
    }
    
    public int[] CooleyTukeyFFTFilter.getTwiddleFactors() {
        return this.twiddleFactors;
    }
    
    public void CooleyTukeyFFTFilter.setTwiddleFactors(int[] twiddleFactors) {
        this.twiddleFactors = twiddleFactors;
    }
    
}
