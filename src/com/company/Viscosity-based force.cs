 #version 430
 uniform float h;
 uniform int numPartcls;
 uniform int sizeX;
 uniform int sizeY;
 uniform int sizeZ;
#define h3 h*h*h
#define h2 h*h
#define e 0.018f
  #define width 1000
            #define height 1
            #define PI_FLOAT 3.1415927410125732421875f
            #define K  250.0f
            #define p0 1.0f
            layout(std430, binding = 0) buffer bbd {float Density[];};
            layout(std430, binding = 1) buffer bbp {float Pressure[];};
            layout(std430,binding = 2)  buffer  bbPO {vec4 Position[];};
            layout(std430,binding=3) buffer  bbo {int Offset[];};
            layout(std430,binding=4) buffer  bbm {float mass[];};
            layout(std430,binding=5) buffer bbf {vec4 Viscosity_based_force[];};
            layout(std430,binding=6) buffer  bbb {vec4 velocity[];};


           layout(local_size_x=256, local_size_y=1,local_size_z = 1) in;
           // layout(local_size_x=25, local_size_y=4,local_size_z = 1) in;

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
                    Viscosity_based_force[index]=vec4(0,0,0,0);
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

                                         const vec3 rNorm = diff / r1;
                                            const float r3 = r2 * r1;
                                            const float W =  -1.0f*(r3 / (2 * h3)) + (r2 / h2) +  (h / (2 * r1)) - 1 ;
                                            vec3 temp4;
                                            temp4.x = velocity[index].x;
                                            temp4.y = velocity[index].y;
                                            temp4.z = velocity[index].z;
                                            vec3 temp5;
                                            temp5.x = velocity[r].x;
                                            temp5.y = velocity[r].y;
                                            temp5.z = velocity[r].z;
                                            temp3 += (mass[r] / mass[index]) * (1.0f / Density[r]) *  (temp5 - temp4)*W * rNorm;
                                            Viscosity_based_force[index].x = temp3.x;
                                            Viscosity_based_force[index].y = temp3.y;
                                            Viscosity_based_force[index].z = temp3.z;
                                           }
                                        }

                                          r++;

                                       }

                                   }
                            }
                     }
           }
           Viscosity_based_force[index] *= e;


         }

