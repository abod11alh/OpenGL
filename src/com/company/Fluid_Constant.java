package com.company;

public class Fluid_Constant extends Constant {

    public Fluid_Constant() {

        h = 0.05f;
        viscosity = 0.018f;
        deltaTime = 0.022f;
        density = 1000.0f ;
        ploy6_smoothing_kernel = (float) (315 /(64*pi *Math.pow(h,9)));
        spiky_smoothing_kernel = (float) (-1*45/(pi*Math.pow(h,6)));

          }
}
