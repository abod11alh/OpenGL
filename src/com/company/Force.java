package com.company;
import com.sun.javafx.geom.Vec3f;
import java.util.*;


public abstract class Force implements Iforce{

    public Force(Fluid ourfluid) {
        this.ourfluid = ourfluid;
    }

    @Override
    public abstract void activeshader();

    @Override
    public abstract void AddBuffers() ;

    protected float value;

    protected  Fluid ourfluid;

    protected Vector3f orientition;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Vector3f getOrientition() {
        return orientition;
    }

    public void setOrientition(Vector3f orientition) {
        this.orientition = orientition;
    }




}