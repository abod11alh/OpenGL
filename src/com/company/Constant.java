package com.company;

import java.util.*;


public class Constant {


    public Constant() {
    }



    protected float h;

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    protected float pressure;


    protected float density;

    protected float pi = 3.1415f;
    protected float ploy6_smoothing_kernel ;

    protected float spiky_smoothing_kernel;

    protected float viscosity;

    protected float deltaTime;

    public float getPloy6_smoothing_kernel() {
        return ploy6_smoothing_kernel;
    }

    public void setPloy6_smoothing_kernel(float ploy6_smoothing_kernel) {
        this.ploy6_smoothing_kernel = ploy6_smoothing_kernel;
    }

    public float getSpiky_smoothing_kernel() {
        return spiky_smoothing_kernel;
    }

    public void setSpiky_smoothing_kernel(float spiky_smoothing_kernel) {
        this.spiky_smoothing_kernel = spiky_smoothing_kernel;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getdensity() {
        return density;
    }

    public void setdensity(float density) {
        density = density;
    }

    public float getviscosity() {
        return viscosity;
    }

    public void setviscosity(float viscosity) {
        this.viscosity = viscosity;
    }

    /**

     * 
     */



}