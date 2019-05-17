package com.company;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBComputeShader.glDispatchCompute;
import static org.lwjgl.opengl.ARBShaderImageLoadStore.glMemoryBarrier;
import static org.lwjgl.opengl.ARBShaderStorageBufferObject.GL_SHADER_STORAGE_BARRIER_BIT;

public class Viscosity_based_forace extends Force {
    int b=1;

    public Viscosity_based_forace(Fluid ourfluid) {
        super(ourfluid);
    }

    @Override
    public void activeshader() {
        ourfluid.getShaderMangement().getProgram().use();

        int H =ourfluid.getShaderMangement().getProgram().getUniformLocation("h");
        ourfluid.getShaderMangement().getProgram().setUniform(H,ourfluid.getConstant().getH());

        int numPartcls=ourfluid.getShaderMangement().getProgram().getUniformLocation("numPartcls");
        ourfluid.getShaderMangement().getProgram().setUniform(numPartcls,ourfluid.getCountpartical());

        int sizeX=ourfluid.getShaderMangement().getProgram().getUniformLocation("sizeX");
        ourfluid.getShaderMangement().getProgram().setUniform(sizeX, (int) (ourfluid.getWidth()/ourfluid.getConstant().getH()));

        int sizeY=ourfluid.getShaderMangement().getProgram().getUniformLocation("sizeY");
        ourfluid.getShaderMangement().getProgram().setUniform(sizeY, (int) (ourfluid.getHeight()/ourfluid.getConstant().getH()));

        int sizeZ=ourfluid.getShaderMangement().getProgram().getUniformLocation("sizeZ");
        ourfluid.getShaderMangement().getProgram().setUniform(sizeZ, (int) (ourfluid.getDepth()/ourfluid.getConstant().getH()));


       // glDispatchCompute(15, 2, 1);
        glDispatchCompute(((3000 + 256 - 1) / 256), 1, 1);


        glMemoryBarrier( GL_SHADER_STORAGE_BARRIER_BIT );
        ourfluid.getSsbo_Density().BindBuffer();

        FloatBuffer f =ourfluid.getSsbo_Density().MapBufferRang().asFloatBuffer();

        ourfluid.setDensity(f);

        ourfluid.getSsbo_Pressur().BindBuffer();
        FloatBuffer f1 =ourfluid.getSsbo_Pressur().MapBufferRang().asFloatBuffer();
        ourfluid.setPressure(f1);

        ourfluid.getSsbo_mass().BindBuffer();
        FloatBuffer f2=ourfluid.getSsbo_mass().MapBufferRang().asFloatBuffer();
        ourfluid.setMass(f2);

        ourfluid.getSsbo_offset().BindBuffer();
        IntBuffer f3=ourfluid.getSsbo_offset().MapBufferRang().asIntBuffer();
        ourfluid.setOffset(f3);

        ourfluid.getSsbo_position().BindBuffer();
        FloatBuffer f4=ourfluid.getSsbo_position().MapBufferRang().asFloatBuffer();
        ourfluid.setPosition(f4);

        ourfluid.getSsbo_viscosity().BindBuffer();
        FloatBuffer f5=ourfluid.getSsbo_viscosity().MapBufferRang().asFloatBuffer();
        ourfluid.setViscosity(f5);

        ourfluid.getSsbo_velosity().BindBuffer();
        FloatBuffer f6=ourfluid.getSsbo_velosity().MapBufferRang().asFloatBuffer();
        ourfluid.setVelosity(f6);


        int count=0;
        int count1=0;

         /*   for (int i = 0; i < 3000; i++) {

                System.out.println(ourfluid.getDensity().get(i) + " D" + i);
                System.out.println(ourfluid.getPressure().get(i) + " P" + i);
                System.out.println(ourfluid.getMass().get(i) + " M" + i);

                System.out.println(ourfluid.getPosition().get(count) + " P:X" + i);
                System.out.println(ourfluid.getPosition().get(count + 1) + " P:Y" + i);
                System.out.println(ourfluid.getPosition().get(count + 2) + " P:Z" + i);
                System.out.println(ourfluid.getPosition().get(count + 3) + " P:W" + i);

                System.out.println(ourfluid.getViscosity().get(count1) + " VF:X" + i);
                System.out.println(ourfluid.getViscosity().get(count1 + 1) + " VF:Y" + i);
                System.out.println(ourfluid.getViscosity().get(count1 + 2) + " VF:Z" + i);
                System.out.println(ourfluid.getViscosity().get(count1 + 3) + " VF:Z" + i);

                System.out.println(ourfluid.getVelosity().get(count1) + " VE:X" + i);
                System.out.println(ourfluid.getVelosity().get(count1 + 1) + " VE:Y" + i);
                System.out.println(ourfluid.getVelosity().get(count1 + 2) + " VE:Z" + i);
                System.out.println(ourfluid.getVelosity().get(count1 + 3) + " VE:Z" + i);

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

        ourfluid.getSsbo_Pressur().BindBuffer();
        ourfluid.getSsbo_Pressur().BufferData(ourfluid.getPressure());
        ourfluid.getSsbo_Pressur().BindBufferBase(1);
        ourfluid.getSsbo_Pressur().BindBuffer(0);


        ourfluid.getSsbo_position().BindBuffer();
        ourfluid.getSsbo_position().BufferData(ourfluid.getPosition());
        ourfluid.getSsbo_position().BindBufferBase(2);
        ourfluid.getSsbo_position().BindBuffer(0);

        ourfluid.getSsbo_offset().BindBuffer();
        ourfluid.getSsbo_offset().BufferData(ourfluid.getOffset());
        ourfluid.getSsbo_offset().BindBufferBase(3);
        ourfluid.getSsbo_offset().BindBuffer(0);


        ourfluid.getSsbo_mass().BindBuffer();
        ourfluid.getSsbo_mass().BufferData(ourfluid.getMass());
        ourfluid.getSsbo_mass().BindBufferBase(4);
        ourfluid.getSsbo_mass().BindBuffer(0);


        ourfluid.getSsbo_viscosity().BindBuffer();
        ourfluid.getSsbo_viscosity().BufferData(ourfluid.getViscosity());
        ourfluid.getSsbo_viscosity().BindBufferBase(5);
        ourfluid.getSsbo_viscosity().BindBuffer(0);


        ourfluid.getSsbo_velosity().BindBuffer();
        ourfluid.getSsbo_velosity().BufferData(ourfluid.getVelosity());
        ourfluid.getSsbo_velosity().BindBufferBase(6);
        ourfluid.getSsbo_velosity().BindBuffer(0);
    }
}
