package com.company;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBComputeShader.glDispatchCompute;
import static org.lwjgl.opengl.ARBShaderImageLoadStore.glMemoryBarrier;
import static org.lwjgl.opengl.ARBShaderStorageBufferObject.GL_SHADER_STORAGE_BARRIER_BIT;

public class DensityEvaluation extends  Force{
    public DensityEvaluation(Fluid ourfluid) {
        super(ourfluid);
    }
int b=1;
    int b1=1;
    @Override
    public void activeshader() {

        ourfluid.getShaderMangement().getProgram().use();
        int pos=ourfluid.getShaderMangement().getProgram().getUniformLocation("ploy6_smoothing_kernel");
        ourfluid.getShaderMangement().getProgram().setUniform(pos,ourfluid.getConstant().getPloy6_smoothing_kernel());

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


        glDispatchCompute(((ourfluid.getCountpartical() + 256 - 1) / 256), 1, 1);
        //glDispatchCompute(15, 2, 1);

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
        ourfluid.getSsbo_Pressure_based_force().BindBufferBase(6);
        ourfluid.getSsbo_Pressure_based_force().BindBuffer(0);


    }
}
