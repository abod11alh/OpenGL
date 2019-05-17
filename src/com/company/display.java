package com.company;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;


public class display {
    private ShaderMangement shaderMangement;
    private VertexBufferObject VBO_Partical;
    private VertexArrayObject VAO_Partical;
    private Matrix4f projection;
    private Matrix4f view;
    private Matrix4f model;
    private FloatBuffer boundry=BufferUtils.createFloatBuffer(8*3);

    public void Init_P(FloatBuffer Position)
    {
        VBO_Partical.bind(GL_ARRAY_BUFFER);
        VBO_Partical.uploadData(GL_ARRAY_BUFFER, Position, GL_STATIC_DRAW);
    }

    public void Init_display(FloatBuffer Position)
    {
        shaderMangement=new ShaderMangement();
        VAO_Partical = new VertexArrayObject();
        VAO_Partical.bind();
        //  try (MemoryStack stack = MemoryStack.stackPush()) {
        /* Vertex data */


        /* Generate Vertex Buffer Object */
        VBO_Partical = new VertexBufferObject();
        VBO_Partical.bind(GL_ARRAY_BUFFER);
        VBO_Partical.uploadData(GL_ARRAY_BUFFER, Position, GL_STATIC_DRAW);
        //  }
        shaderMangement.InitShader_fragment_vertex();
        specifyVertexAttributes();
        /* Get uniform location for the model matrix */

        /* Set view matrix to identity matrix */
         view = new Matrix4f();
        int uniView = shaderMangement.getProgram_VF().getUniformLocation("view");
        shaderMangement.getProgram_VF().setUniform(uniView, view);



        /* Get width and height for calculating the ratio */
       /* float ratio;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            long window = GLFW.glfwGetCurrentContext();
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, width, height);
            ratio = width.get() / (float) height.get();
        }*

        /* Set projection matrix to an orthographic projection */
         projection = Matrix4f.orthographic(-1f, 1f, -1f, 1f, -1f, 1f);
        int uniProjection = shaderMangement.getProgram_VF().getUniformLocation("projection");
        shaderMangement.getProgram_VF().setUniform(uniProjection, projection);



    }
    public  void begin_display()
    {
        int uniModel = shaderMangement.getProgram_VF().getUniformLocation("model");
        VAO_Partical.bind();
        shaderMangement.getProgram_VF().use();
         model =new Matrix4f();
        shaderMangement.getProgram_VF().setUniform(uniModel, model);

        glDrawArrays(GL_POINTS, 0, 6000);


    }
    private void specifyVertexAttributes() {
        /* Specify Vertex Pointer */
        int posAttrib = shaderMangement.getProgram_VF().getAttributeLocation("position");
        shaderMangement.getProgram_VF().enableVertexAttribute(posAttrib);
        shaderMangement.getProgram_VF().pointVertexAttribute(posAttrib, 3, 6 * Float.BYTES, 0);

        /* Specify Color Pointer */
        int colAttrib = shaderMangement.getProgram_VF().getAttributeLocation("color");
        shaderMangement.getProgram_VF().enableVertexAttribute(colAttrib);
        shaderMangement.getProgram_VF().pointVertexAttribute(colAttrib,1 , 6 * Float.BYTES, 3 * Float.BYTES);
    }
    public void disposs()
    {
        VAO_Partical.delete();
        VBO_Partical.delete();
        shaderMangement.getVertex().delete();
        shaderMangement.getFragmaent().delete();
        shaderMangement.getProgram_VF().delete();
    }
}
