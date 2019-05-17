#version 430
uniform float deltaTime;
#define WALL_DAMPING 0.4f

            layout(std430,binding = 0) buffer bbd {float Density[];};
            layout(std430,binding = 1) buffer bbPO {vec4 Position[];};
            layout(std430,binding = 2) buffer bbv {vec4 Viscosity_based_force[];};
            layout(std430,binding = 3) buffer bbb {vec4 velocity[];};
            layout(std430,binding = 4) buffer bbf {vec4 Pressure_based_force[];};



          //layout(local_size_x=10, local_size_y=10,local_size_z = 1) in;
                   layout(local_size_x=256, local_size_y=1,local_size_z = 1) in;

            void main()
            {
                    uint index = gl_LocalInvocationID.x+gl_LocalInvocationID.y*gl_WorkGroupSize.x+(gl_WorkGroupID.x+gl_WorkGroupID.y*gl_NumWorkGroups.x)*gl_WorkGroupSize.x*gl_WorkGroupSize.y;
                    const vec3 G = vec3(0, -9.8f, 0);
                    vec3 acceleration = ((vec3(Pressure_based_force[index]) +vec3(Viscosity_based_force[index])) / Density[index] + G);
                        vec3 new_velocity = vec3(velocity[index]) + deltaTime * acceleration;
                        vec3 new_position = vec3(Position[index]) + deltaTime * new_velocity;

                        // boundary conditions
                        if (new_position.x < 0)
                        {
                            new_position.x = 0;
                            new_velocity.x *= -1 * WALL_DAMPING;
                        }
                         if (new_position.x > 1)
                        {
                            new_position.x = 1;
                            new_velocity.x *= -1 * WALL_DAMPING;
                        }
                         if (new_position.y < 0)
                        {
                            new_position.y = 0;
                            new_velocity.y *= -1 * WALL_DAMPING;
                        }
                         if (new_position.y > 1)
                        {
                            new_position.y = 1;
                            new_velocity.y *= -1 * WALL_DAMPING;
                        }
                         if (new_position.z < 0)
                         {
                             new_position.z = 0;
                             new_velocity.z *= -1 * WALL_DAMPING;
                         }
                         if (new_position.z> 1)
                         {
                              new_position.z = 1;
                              new_velocity.z *= -1 * WALL_DAMPING;
                          }


                        velocity[index] = vec4(new_velocity,0.0f);
                        Position[index] = vec4(new_position,0.0f);



            }