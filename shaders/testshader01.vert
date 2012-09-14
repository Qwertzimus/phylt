varying vec2 texCoord;
attribute float lightValue;
uniform float baseLight;
varying float light;
void main(){
	texCoord=gl_MultiTexCoord0;
	light=lightValue+baseLight;
	gl_Position= gl_ModelViewProjectionMatrix*gl_Vertex;
}