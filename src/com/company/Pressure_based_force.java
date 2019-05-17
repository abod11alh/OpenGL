package com.company;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBComputeShader.glDispatchCompute;
import static org.lwjgl.opengl.ARBShaderImageLoadStore.glMemoryBarrier;
import static org.lwjgl.opengl.ARBShaderStorageBufferObject.GL_SHADER_STORAGE_BARRIER_BIT;

public class Pressure_based_force extends Force {
    int b=1;
    public Pressure_based_force(Fluid ourfluid) {
        super(ourfluid);
    }

    @Override
    public void activeshader() {
        ourfluid.getShaderMangement().getProgram_f().use();
        int pos=ourfluid.getShaderMangement().getProgram_f().getUniformLocation("spiky_smoothing_kernel");
        ourfluid.getShaderMangement().getProgram_f().setUniform(pos,ourfluid.getConstant().getSpiky_smoothing_kernel());

        int H =ourfluid.getShaderMangement().getProgram_f().getUniformLocation("h");
        ourfluid.getShaderMangement().getProgram_f().setUniform(H,ourfluid.getConstant().getH());
        int numPartcls=ourfluid.getShaderMangement().getProgram_f().getUniformLocation("numPartcls");
        ourfluid.getShaderMangement().getProgram_f().setUniform(numPartcls,ourfluid.getCountpartical());

        int sizeX=ourfluid.getShaderMangement().getProgram_f().getUniformLocation("sizeX");
        ourfluid.getShaderMangement().getProgram_f().setUniform(sizeX, (int) (ourfluid.getWidth()/ourfluid.getConstant().getH()));
        int sizeY=ourfluid.getShaderMangement().getProgram_f().getUniformLocation("sizeY");
        ourfluid.getShaderMangement().getProgram_f().setUniform(sizeY, (int) (ourfluid.getHeight()/ourfluid.getConstant().getH()));

        int sizeZ=ourfluid.getShaderMangement().getProgram_f().getUniformLocation("sizeZ");
        ourfluid.getShaderMangement().getProgram_f().setUniform(sizeZ, (int) (ourfluid.getDepth()/ourfluid.getConstant().getH()));


        glDispatchCompute(((ourfluid.getCountpartical() + 128 - 1) / 128), 1, 1);
       // glDispatchCompute(15, 2, 1);


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
        FloatBuffer f6=ourfluid.getSsbo_viscosity().MapBufferRang().asFloatBuffer();
        ourfluid.setViscosity(f6);

        ourfluid.getSsbo_velosity().BindBuffer();
        FloatBuffer f7=ourfluid.getSsbo_velosity().MapBufferRang().asFloatBuffer();
        ourfluid.setVelosity(f7);

        ourfluid.getSsbo_Pressure_based_force().BindBuffer();
        FloatBuffer f5=ourfluid.getSsbo_Pressure_based_force().MapBufferRang().asFloatBuffer();
        ourfluid.setPressure_based_force1(f5);
      /*  int count=0;
        int count1=0;


        for (int i = 0; i < 3000; i++) {

            System.out.println(ourfluid.getDensity().get(i) + " D" + i);
            System.out.println(ourfluid.getPressure().get(i) + " P" + i);
            System.out.println(ourfluid.getMass().get(i) + " M" + i);

            System.out.println(ourfluid.getPosition().get(count) + " P:X" + i);
            System.out.println(ourfluid.getPosition().get(count + 1) + " P:Y" + i);
            System.out.println(ourfluid.getPosition().get(count + 2) + " P:Z" + i);
            System.out.println(ourfluid.getPosition().get(count + 3) + " P:W" + i);

            System.out.println(ourfluid.getPressure_based_force1().get(count1) + " PF:X" + i);
            System.out.println(ourfluid.getPressure_based_force1().get(count1 + 1) + " PF:Y" + i);
            System.out.println(ourfluid.getPressure_based_force1().get(count1 + 2) + " PF:Z" + i);
            System.out.println(ourfluid.getPressure_based_force1().get(count1 + 3) + " PF:Z" + i);



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

        ourfluid.getSsbo_Pressure_based_force().BindBuffer();
        ourfluid.getSsbo_Pressure_based_force().BufferData(ourfluid.getPressure_based_force1());
        ourfluid.getSsbo_Pressure_based_force().BindBufferBase(5);
        ourfluid.getSsbo_Pressure_based_force().BindBuffer(0);

        ourfluid.getSsbo_viscosity().BindBuffer();
        ourfluid.getSsbo_viscosity().BufferData(ourfluid.getViscosity());
        ourfluid.getSsbo_viscosity().BindBufferBase(6);
        ourfluid.getSsbo_viscosity().BindBuffer(0);


        ourfluid.getSsbo_velosity().BindBuffer();
        ourfluid.getSsbo_velosity().BufferData(ourfluid.getVelosity());
        ourfluid.getSsbo_velosity().BindBufferBase(7);
        ourfluid.getSsbo_velosity().BindBuffer(0);
    }
}
