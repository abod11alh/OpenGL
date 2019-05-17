package com.company;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBComputeShader.glDispatchCompute;
import static org.lwjgl.opengl.ARBShaderImageLoadStore.glMemoryBarrier;
import static org.lwjgl.opengl.ARBShaderStorageBufferObject.GL_SHADER_STORAGE_BARRIER_BIT;

public class Integrate extends  Force{
    int b=1;
    public Integrate(Fluid ourfluid) {
        super(ourfluid);
    }

    @Override
    public void activeshader() {
        ourfluid.getShaderMangement().getProgram_I().use();
        int pos=ourfluid.getShaderMangement().getProgram_I().getUniformLocation("deltaTime");
        ourfluid.getShaderMangement().getProgram_I().setUniform(pos,ourfluid.getConstant().getDeltaTime());



      //  glDispatchCompute(15, 2, 1);
       glDispatchCompute(((ourfluid.getCountpartical() + 256 - 1) / 256), 1, 1);


        glMemoryBarrier( GL_SHADER_STORAGE_BARRIER_BIT );







        ourfluid.getSsbo_position().BindBuffer();
        FloatBuffer f=ourfluid.getSsbo_position().MapBufferRang().asFloatBuffer();
        ourfluid.setPosition(f);


        ourfluid.getSsbo_velosity().BindBuffer();
        FloatBuffer f5=ourfluid.getSsbo_velosity().MapBufferRang().asFloatBuffer();
        ourfluid.setVelosity(f5);

     //   int count=0;
       // int count1=0;

          /*  for (int i = 0; i < 3000; i++) {



                System.out.println(ourfluid.getPosition().get(count) + " P:X" + i);
                System.out.println(ourfluid.getPosition().get(count + 1) + " P:Y" + i);
                System.out.println(ourfluid.getPosition().get(count + 2) + " P:Z" + i);
                System.out.println(ourfluid.getPosition().get(count + 3) + " P:W" + i);

                System.out.println(ourfluid.getVelosity().get(count1) + " V:X" + i);
                System.out.println(ourfluid.getVelosity().get(count1 + 1) + " V:Y" + i);
                System.out.println(ourfluid.getVelosity().get(count1 + 2) + " V:Z" + i);
                System.out.println(ourfluid.getVelosity().get(count1 + 3) + " V:W" + i);

                System.out.println(ourfluid.getDensity().get(i) + " V:W" + i);

                count+=4;
                count1+=4;
                System.out.println("---------------------------------");


            }
            System.out.println("*********************************************");*/


    }

    @Override
    public void AddBuffers() {
        ourfluid.getSsbo_Density().BindBuffer();
        ourfluid.getSsbo_Density().BufferData(ourfluid.getDensity());
        ourfluid.getSsbo_Density().BindBufferBase(0);
        ourfluid.getSsbo_Density().BindBuffer(0);

        ourfluid.getSsbo_position().BindBuffer();
        ourfluid.getSsbo_position().BufferData(ourfluid.getPosition());
        ourfluid.getSsbo_position().BindBufferBase(1);
        ourfluid.getSsbo_position().BindBuffer(0);

        ourfluid.getSsbo_viscosity().BindBuffer();
        ourfluid.getSsbo_viscosity().BufferData(ourfluid.getViscosity());
        ourfluid.getSsbo_viscosity().BindBufferBase(2);
        ourfluid.getSsbo_viscosity().BindBuffer(0);

        ourfluid.getSsbo_velosity().BindBuffer();
        ourfluid.getSsbo_velosity().BufferData(ourfluid.getVelosity());
        ourfluid.getSsbo_velosity().BindBufferBase(3);
        ourfluid.getSsbo_velosity().BindBuffer(0);

        ourfluid.getSsbo_Pressure_based_force().BindBuffer();
        ourfluid.getSsbo_Pressure_based_force().BufferData(ourfluid.getPressure_based_force1());
        ourfluid.getSsbo_Pressure_based_force().BindBufferBase(4);
        ourfluid.getSsbo_Pressure_based_force().BindBuffer(0);

    }
}
