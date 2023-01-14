#version 460 core

layout (location = 0) in vec4 position;

uniform mat4 projection;
uniform mat4 model_view;

out gl_PerVertex {
    vec4 gl_Position;
};

void main() {
    gl_Position = projection * model_view * position;
}
