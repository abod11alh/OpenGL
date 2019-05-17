package com.company;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBShaderStorageBufferObject.GL_SHADER_STORAGE_BUFFER;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;

public class SSBO {
    private  final int id;


    public SSBO() { id = glGenBuffers(); }
    public  void BindBuffer()
    {
        glBindBuffer( GL_SHADER_STORAGE_BUFFER, id );
    }
    public  void BindBuffer(int num)
    {
        glBindBuffer( GL_SHADER_STORAGE_BUFFER, num );
    }
    public void BufferData( FloatBuffer data)
    {
        glBufferData( GL_SHADER_STORAGE_BUFFER, data, GL_STATIC_DRAW );
    }
    public void BufferData( IntBuffer data)
    {
        glBufferData( GL_SHADER_STORAGE_BUFFER, data, GL_STATIC_DRAW );
    }
    public ByteBuffer MapBufferRang()
    {
        int BufMap= GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT ;

        return glMapBuffer(GL_SHADER_STORAGE_BUFFER, GL_READ_ONLY);

    }
    public ByteBuffer MapBufferRang(int x,int y)
    {

        int BufMap= GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT ;
        return  glMapBufferRange(GL_SHADER_STORAGE_BUFFER,x,y,BufMap);
    }
    public void BindBufferBase(int index)
    {
        glBindBufferBase(GL_SHADER_STORAGE_BUFFER, index, id);
    }

    public  void BindBufferRange(int index,int x,int y)
    {
        glBindBufferRange(GL_SHADER_STORAGE_BUFFER,index , id, x, y);

    }
    public  void  addFloatBuffer(FloatBuffer floatBuffer,int index)
    {
        BufferData(floatBuffer);
    }
}
