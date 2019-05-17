package com.company;

import java.io.IOException;

import static org.lwjgl.opengl.GL20C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL43C.GL_COMPUTE_SHADER;

public class ShaderMangement {
    private ShaderProgram program;
    private ShaderProgram program_f;
    private ShaderProgram program_I;
    private Shader DensityEvaluation;
    private Shader Pressure_based_force;
    private Shader Viscosity_based_force;
    private Shader Integrate;
    private Shader fragmaent;
    private Shader vertex;
    private ShaderProgram program_VF;

    public ShaderMangement() {
        program_I = new ShaderProgram();
        program = new ShaderProgram();
        program_f = new ShaderProgram();



    }

    public ShaderProgram getProgram_f() {
        return program_f;
    }

    public void setProgram_f(ShaderProgram program_f) {
        this.program_f = program_f;
    }

    public ShaderProgram getProgram_I() {
        return program_I;
    }

    public void setProgram_I(ShaderProgram program_I) {
        this.program_I = program_I;
    }

    public ShaderProgram getProgram_VF() {
        return program_VF;
    }

    public Shader getFragmaent() {
        return fragmaent;
    }

    public void setFragmaent(Shader fragmaent) {
        this.fragmaent = fragmaent;
    }

    public Shader getVertex() {
        return vertex;
    }

    public void setVertex(Shader vertex) {
        this.vertex = vertex;
    }

    public void setProgram_VF(ShaderProgram program_VF) {
        this.program_VF = program_VF;
    }

    public ShaderProgram getProgram() {
        return program;
    }

    public void setProgram(ShaderProgram program) {
        this.program = program;
    }

    public Shader getDensityEvaluation() {
        return DensityEvaluation;
    }

    public void setDensityEvaluation(Shader densityEvaluation) {
        DensityEvaluation = densityEvaluation;
    }

    public void InitShaders_DensityEvaluation() {


        try {
            DensityEvaluation = Shader.createShader(GL_COMPUTE_SHADER, StringFromFile.getString("C://Users//Abdulrahman//Desktop//other//Open GL//src//com//company//DensityEvaluation.cs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        program.attachShader(DensityEvaluation);


        DensityEvaluation.delete();
        program.link();


    }

    public void InitShaders_Pressure_based_force() {

        try {
            Pressure_based_force = Shader.createShader(GL_COMPUTE_SHADER, StringFromFile.getString("C://Users//Abdulrahman//Desktop//other//Open GL//src//com//company//Pressure-based force1.cs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        program_f.attachShader(Pressure_based_force);

        Pressure_based_force.delete();
        program_f.link();

    }

    public void InitShader_Viscosity_based_force() {
        program = new ShaderProgram();

        try {
            Viscosity_based_force = Shader.createShader(GL_COMPUTE_SHADER, StringFromFile.getString("C://Users//Abdulrahman//Desktop//other//Open GL//src//com//company//Viscosity-based force.cs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        program.attachShader(Viscosity_based_force);
        Viscosity_based_force.delete();
        program.link();
    }
    public void InitShader_integrate() {

        try {
            Integrate = Shader.createShader(GL_COMPUTE_SHADER, StringFromFile.getString("C://Users//Abdulrahman//Desktop//other//Open GL//src//com//company//integrate.cs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        program_I.attachShader(Integrate);
        Integrate.delete();
        program_I.link();
    }
    public  void InitShader_fragment_vertex()
    {


        try {
            fragmaent = Shader.createShader(GL_FRAGMENT_SHADER, StringFromFile.getString("C://Users//Abdulrahman//Desktop//other//New Openl gl//Open GL//src//com//company//frag.fs"));
            vertex = Shader.createShader(GL_VERTEX_SHADER, StringFromFile.getString("C://Users//Abdulrahman//Desktop//other//New Openl gl//Open GL//src//com//company//vret.vs"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        program_VF = new ShaderProgram();
        program_VF.attachShader(vertex);
        program_VF.attachShader(fragmaent);
        program_VF.bindFragmentDataLocation(0, "fragColor");
        program_VF.link();
        program_VF.use();

    }
}
