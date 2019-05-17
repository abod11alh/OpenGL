 #version 430
 uniform float h;
 uniform float spiky_smoothing_kernel ;
 uniform int numPartcls;
 uniform int sizeX;
 uniform int sizeY;
 uniform int sizeZ;
#define h3 h*h*h
#define h2 h*h
#define e 0.018f
            layout(std430, binding = 0) buffer bbd {float Density[];};
            layout(std430, binding = 1) buffer bbp {float Pressure[];};
            layout(std430, binding = 2)  buffer  bbPO {vec4 Position[];};
            layout(std430, binding = 3) buffer  bbo {int Offset[];};
            layout(std430, binding = 4) buffer  bbm {float mass[];};
            layout(std430, binding = 5) buffer bbf {vec4 Pressure_based_force[];};
            layout(std430, binding = 6) buffer bbff {vec4 Viscosity_based_force[];};
            layout(std430, binding = 7) buffer  bbb {vec4 velocity[];};
            layout(local_size_x=256, local_size_y=1,local_size_z = 1) in;
          //layout(local_size_x=10, local_size_y=10,local_size_z = 1) in;

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
                    vec3 temp3=vec3(0,0,0);
                    vec3 tempV=vec3(0,0,0);
                    Pressure_based_force[index]=vec4(0,0,0,0);
                    Viscosity_based_force[index]=vec4(0,0,0,0);
                    int i,j,k;
                     for(i = -1 ;i<=1 ;i++){
                       for(j = -1 ;j<=1 ;j++){
                           for(k = -1 ;k<=1 ;k++){
                                   const ivec3 neighborIndex = ivec3(cellIndex) + ivec3(i,j,k);

                                  if(!isAccept(neighborIndex.x , neighborIndex.y , neighborIndex.z))continue ;
                                   int flatneighborIndex = neighborIndex.x+ neighborIndex.y * sizeX+neighborIndex.z * sizeY * sizeX ;
                                   const int neighborOffset = Offset[ flatneighborIndex];

                                   if(neighborOffset!=-1)
                                   {
                                       int r=neighborOffset;

                                       float temp=flatneighborIndex;
                                       while(r<numPartcls&&Position[r].w==flatneighborIndex)
                                       {

                                         const vec3 diff=vec3(Position[index]) - vec3(Position[r]);
                                         const float r2 = dot(diff, diff);
                                         float r1 = sqrt(r2);

                                         if(r1<(h))
                                         {

                                        if(r1!=0.0f)
                                         {

                                           const vec3 rNorm = diff / r1 ;
                                           const  float  W = spiky_smoothing_kernel * pow(h - r1, 2);
                                           temp3+=( mass[r]/mass[index])*((Pressure[index]+Pressure[r])/(2* Density[index]*Density[r]))*W *rNorm;
                                           Pressure_based_force[index]=vec4(temp3,0);



                                            const float r3 = r2 * r1;
                                            const float W1 =  -1.0f*(r3 / (2 * h3)) + (r2 / h2) +  (h / (2 * r1)) - 1 ;
                                            vec3 temp4=vec3(velocity[index]);

                                            vec3 temp5=vec3( velocity[r]);
                                            tempV=vec3(Viscosity_based_force[index]);

                                            tempV += (mass[r] / mass[index]) * (1.0f / Density[r]) *  (temp5 - temp4)*W1 * rNorm;
                                            Viscosity_based_force[index]=vec4(tempV,0);
                                           }
                                        }

                                          r++;

                                       }
                                   }
                            }
                     }
           }
             Pressure_based_force[index] *= -1;
           Viscosity_based_force[index] *= e;
         }
