package com.company;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

public class Fluid {
    private List<Partical> particals;


    private int countpartical;
    private int height=1,width=1,depth=1;
    private  Constant constant;
    private  Grid grid;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    private  FloatBuffer density;
    private  FloatBuffer mass;
    private  FloatBuffer Pressure_based_force1;
    private  FloatBuffer pressure;
    private  FloatBuffer position;
    private FloatBuffer viscosity;
    private FloatBuffer velosity;
    private IntBuffer  offset;
    private  FloatBuffer positiontemp;
    private SSBO ssbo_Density;
    private SSBO ssbo_Pressur;
    private SSBO ssbo_position;
    private SSBO ssbo_mass;
    private SSBO ssbo_offset;
    private SSBO ssbo_Pressure_based_force;
    private SSBO ssbo_viscosity;
    private SSBO ssbo_velosity;
    private  DensityEvaluation activedensityEvaluation;
    private ShaderMangement shaderMangement;
    private  Pressure_based_force pressureBasedForce;
    private Viscosity_based_forace viscosity_based_forace;
    private  Integrate integrate;

    public FloatBuffer getPositiontemp() {
        return positiontemp;
    }

    public void setPositiontemp(FloatBuffer positiontemp) {
        this.positiontemp = positiontemp;
    }

    public FloatBuffer getViscosity() {
        return viscosity;
    }

    public void setViscosity(FloatBuffer viscosity) {
        this.viscosity = viscosity;
    }

    public FloatBuffer getVelosity() {
        return velosity;
    }

    public void setVelosity(FloatBuffer velosity) {
        this.velosity = velosity;
    }

    public SSBO getSsbo_viscosity() {
        return ssbo_viscosity;
    }

    public void setSsbo_viscosity(SSBO ssbo_viscosity) {
        this.ssbo_viscosity = ssbo_viscosity;
    }

    public SSBO getSsbo_velosity() {
        return ssbo_velosity;
    }

    public void setSsbo_velosity(SSBO ssbo_velosity) {
        this.ssbo_velosity = ssbo_velosity;
    }

    public FloatBuffer getPressure_based_force1() {
        return Pressure_based_force1;
    }

    public void setPressure_based_force1(FloatBuffer pressure_based_force1) {
        Pressure_based_force1 = pressure_based_force1;
    }

    public DensityEvaluation getActivedensityEvaluation() {
        return activedensityEvaluation;
    }

    public void setActivedensityEvaluation(DensityEvaluation activedensityEvaluation) {
        this.activedensityEvaluation = activedensityEvaluation;
    }

    public ShaderMangement getShaderMangement() {
        return shaderMangement;
    }

    public void setShaderMangement(ShaderMangement shaderMangement) {
        this.shaderMangement = shaderMangement;
    }

    public IntBuffer getOffset() {
        return offset;
    }

    public void setOffset(IntBuffer offset) {
        this.offset = offset;
    }

    public FloatBuffer getPosition() {
        return position;
    }

    public void setPosition(FloatBuffer position) {
        this.position = position;
    }


    public SSBO getSsbo_Pressure_based_force() {
        return ssbo_Pressure_based_force;
    }

    public void setSsbo_Pressure_based_force(SSBO ssbo_Pressure_based_force) {
        this.ssbo_Pressure_based_force = ssbo_Pressure_based_force;
    }

    public SSBO getSsbo_position() {
        return ssbo_position;
    }

    public void setSsbo_position(SSBO ssbo_position) {
        this.ssbo_position = ssbo_position;
    }

    public SSBO getSsbo_offset() {
        return ssbo_offset;
    }

    public void setSsbo_offset(SSBO ssbo_offset) {
        this.ssbo_offset = ssbo_offset;
    }

    public SSBO getSsbo_Density() {
        return ssbo_Density;
    }

    public void setSsbo_Density(SSBO ssbo_Density) {
        this.ssbo_Density = ssbo_Density;
    }

    public SSBO getSsbo_Pressur() {
        return ssbo_Pressur;
    }

    public void setSsbo_Pressur(SSBO ssbo_Prusser) {
        this.ssbo_Pressur = ssbo_Prusser;
    }

    public FloatBuffer getDensity() {
        return density;
    }

    public void setDensity(FloatBuffer density) {
        this.density = density;
    }

    public FloatBuffer getPressure() {
        return pressure;
    }

    public void setPressure(FloatBuffer pressure) {
        this.pressure = pressure;
    }

    public void InitBuffers()
    {
        density= BufferUtils.createFloatBuffer(countpartical);
        pressure= BufferUtils.createFloatBuffer(countpartical);
        offset=BufferUtils.createIntBuffer(grid.getCellscount());
        viscosity = BufferUtils.createFloatBuffer(countpartical*4);
        Pressure_based_force1 =BufferUtils.createFloatBuffer(countpartical*4);
    }

    public Fluid(int countpartical, Constant constant) {
        this.countpartical = countpartical;
        this.constant=constant;
        grid =new Grid((int) ((height*width*depth)/Math.pow(constant.getH(),3)));
        InitBuffers();
        position=BufferUtils.createFloatBuffer(countpartical*4);
        velosity = BufferUtils.createFloatBuffer(countpartical*4);
        mass = BufferUtils.createFloatBuffer(countpartical);
                particals=new ArrayList<>();

        int j=1,n=0;
        int x=10,y=0 , z =0;
        for (int i = 0; i <countpartical; i++) {
            Partical temp=new Partical();
            temp.getPosition().x=  0.005f * 2* x;
            temp.getPosition().y= 0.005f *2* y;
         //   temp.getPosition().z= 0.005f *2*z;
            mass.put(0.2f) ;


            x++;
            if (x >= 50)
            {
                x = 10;
                y++;

            }
            /*if (y>=100)
            {
                y=0;
                z++;
            }*/
            particals.add(temp);
        }
        mass.flip();
        shaderMangement=new ShaderMangement();
        activedensityEvaluation=new DensityEvaluation(this);
        pressureBasedForce=new Pressure_based_force(this);
        viscosity_based_forace = new Viscosity_based_forace(this);
        integrate=new Integrate(this);
        shaderMangement.InitShaders_DensityEvaluation();
        shaderMangement.InitShaders_Pressure_based_force();
        shaderMangement.InitShader_integrate();

    }




    public List<Partical> getParticalsl() {
        return particals;
    }

    public void setParticalsl(List<Partical> particalsl) {
        this.particals = particalsl;
    }

    public int getCountpartical() {
        return countpartical;
    }

    public void setCountpartical(int countpartica) {
        this.countpartical = countpartica;
    }

    public Constant getConstant() {
        return constant;
    }

    public void setConstant(Constant constant) {
        this.constant = constant;
    }

    public  void FillGrid()
    {
        float x,y,z;
        int I,J,K;
        int cellindex;
        for (int i = 0; i < countpartical; i++) {
           x= particals.get(i).getPosition().x;
            y= particals.get(i).getPosition().y;
            z= particals.get(i).getPosition().z;
            I= (int) Math.floor(x/constant.getH());
            J= (int) Math.floor(y/constant.getH());
            K= (int) Math.floor(z/constant.getH());
            if (I==  width/constant.getH())
            {
                I= (int) (width/constant.getH()-1);
            }
            if (J==height/constant.getH())
            {
                J= (int) (height/constant.getH()-1);
            }
            if (K==depth/constant.getH())
            {
                K= (int) (depth/constant.getH()-1);
            }
            cellindex= (int) (I + J*(width/constant.getH())+K*(width/constant.getH())*(height/constant.getH()));
            grid.getCells().get(cellindex).add(particals.get(i));

        }
    }
public  void fluid_loop()
{
    InitSsbo();
    FillGrid();
    FillBuffers();
    activedensityEvaluation.AddBuffers();
    activedensityEvaluation.activeshader();

    InitSsbo();
    pressureBasedForce.AddBuffers();
    pressureBasedForce.activeshader();

    /*InitSsbo();
    shaderMangement.InitShader_Viscosity_based_force();
    viscosity_based_forace.AddBuffers();
    viscosity_based_forace.activeshader();*/

    InitSsbo();
    integrate.AddBuffers();
    integrate.activeshader();
    FromBuffer();
    InitBuffers();


}
public void InitSsbo()
{
    ssbo_Density=new SSBO();
    ssbo_Pressur=new SSBO();
    ssbo_position=new SSBO();
    ssbo_offset=new SSBO();
    ssbo_mass=new SSBO();
    ssbo_Pressure_based_force=new SSBO();
    ssbo_viscosity = new SSBO();
    ssbo_velosity = new SSBO();
}
    public void FillBuffers()
    {

        int count = 0 , count1 ;
        for (int i = 0; i < grid.getCells().size() ; i++) {

            LinkedList<Partical> linkedlist = grid.getCells().get(i) ;
            count1 = count ;
            while(!linkedlist.isEmpty())
            {
                Partical par = linkedlist.pollFirst();
                count1++ ;
                par.getPosition().toBuffer(position);
                position.put(i);

            //    density.put(par.getDensity()) ;
              //  pressure.put(par.getPrusser()) ;

            }

            if(count1 == count){offset.put(-1);}
            else{ offset.put(count) ; count = count1 ;}



        }
        offset.flip() ;
        //density.flip();
        //pressure.flip();
        position.flip();

    }

    public FloatBuffer getMass() {
        return mass;
    }

    public void setMass(FloatBuffer mass) {
        this.mass = mass;
    }

    public SSBO getSsbo_mass() {
        return ssbo_mass;
    }

    public void setSsbo_mass(SSBO ssbo_mass) {
        this.ssbo_mass = ssbo_mass;
    }

    public void FromBuffer()
    {
        int count = 0 ;
        for (int i = 0; i < countpartical; i++) {

            Vector3f position_temp = new Vector3f(position.get(count), position.get(count + 1), position.get(count + 2));
            particals.get(i).setPosition(position_temp);
            particals.get(i).setCell((int) position.get(count + 3));
            count += 4;
            particals.get(i).setDensity(density.get(i));
            particals.get(i).setPrusser(pressure.get(i));
            particals.get(i).setMass(mass.get(i));

        }
    }

}