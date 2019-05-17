package com.company;



import java.util.*;


public class Partical {

    public Partical() {
        Density=0.0f;
        prusser=0.0f;
        mass =0.2f ;
        position=new Vector3f();
    }

    public Partical(Vector3f position) {
        this.position = position;
    }

    private int cell;

    private float mass ;

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    private Vector3f position;


    private Vector3f velocity;


    private float Density;


    private float totalforce;

    private  float prusser;

    public float getPrusser() {
        return prusser;
    }

    public void setPrusser(float prusser) {
        this.prusser = prusser;
    }

    private float viscosity;

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public float getDensity() {
        return Density;
    }

    public void setDensity(float density) {
        Density = density;
    }

    public float getTotalforce() {
        return totalforce;
    }

    public void setTotalforce(float totalforce) {
        this.totalforce = totalforce;
    }

    public float getViscosity() {
        return viscosity;
    }

    public void setViscosity(float viscosity) {
        this.viscosity = viscosity;
    }
}