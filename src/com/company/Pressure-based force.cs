 #version 430
 uniform float h;
 uniform float spiky_smoothing_kernel ;
 uniform int numPartcls;
 uniform int sizeX;
 uniform int sizeY;
 uniform int sizeZ;

  #define width 1000
            #define height 1
            #define PI_FLOAT 3.1415927410125732421875f
            #define K  250.0f
            #define p0 1.0f
            #define h2 h*h
            layout(std430, binding = 0) buffer bbd {float Density[];};
            layout(std430, binding = 1) buffer bbp {float Pressure[];};
            layout(std430,binding = 2)  buffer  bbPO {vec4 Position[];};
            layout(std430,binding=3) buffer  bbo {int Offset[];};
            layout(std430,binding=4) buffer  bbm {float mass[];};
            layout(std430,binding=6) buffer bbf {vec4 Pressure_based_force[];};

            layout(local_size_x=25, local_size_y=4,local_size_z = 1) in;
         //  layout(local_size_x=256, local_size_y=1,local_size_z = 1) in;

            bool isAccept(int x , int y , int  z)
            {
                if(x<0||y<0||z<0||x>=sizeX||y>=sizeY||z>=sizeZ)return false ;

                return true ;
            }
             void main()
            {

                    uint index = gl_LocalInvocationID.x+gl_LocalInvocationID.y*gl_WorkGroupSize.x+(gl_WorkGroupID.x+gl_WorkGroupID.y*gl_NumWorkGroups.x)*gl_WorkGroupSize.x*gl_WorkGroupSize.y;
                    vec4 remappedPos = Position[index]/h;
                    vec3 cellIndex = floor(vec3(remappedPos));
                                           vec3 temp3;

                    int i,j,k;
                     for(i = -1 ;i<=1 ;i++){
                       for(j = -1 ;j<=1 ;j++){

                           for(k = -1 ;k<=1 ;k++){
                                   const ivec3 neighborIndex = ivec3(cellIndex.x,cellIndex.y,cellIndex.z) + ivec3(i,j,k);

                                  if(!isAccept(neighborIndex.x , neighborIndex.y , neighborIndex.z))continue ;
                                   int flatneighborIndex = neighborIndex.x+ neighborIndex.y * sizeX+neighborIndex.z * sizeY * sizeX ;
                                   const int neighborOffset = Offset[ flatneighborIndex];

                                   if(neighborOffset!=-1)
                                   {
                                       int r=neighborOffset;

                                              float temp=flatneighborIndex;
                                       while(r<numPartcls&&Position[r].w==temp)
                                       {

                                         const vec3 diff=vec3(Position[index].x,Position[index].y,Position[index].z) - vec3(Position[r].x,Position[r].y,Position[r].z);
                                          const float r2 = dot(diff, diff);
                                           float r1 = sqrt(r2);

                                         if(r1<(h))
                                         {

                                        if(r1!=0.0f)
                                         {

                                           const vec3 rNorm = diff / r1 ;

                                           const  float  W = spiky_smoothing_kernel * pow(h - r1, 2);

                                           temp3+=( mass[r]/mass[index])*((Pressure[index]+Pressure[r])/(2* Density[index]*Density[r]))*W *rNorm;
                                           Pressure_based_force[index].x=temp3.x;
                                           Pressure_based_force[index].y=temp3.y;
                                           Pressure_based_force[index].z=temp3.z;
                                           }
                                        }

                                          r++;

                                       }
                                   }
                            }
                     }
           }
             Pressure_based_force[index] *= -1;

         }
