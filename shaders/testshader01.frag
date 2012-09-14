precision highp float;
uniform sampler2D texure;
varying vec2 texCoord;
varying float light;
void main(){
	vec4 colorRGBA=texture2D(texure,texCoord);
	gl_FragColor= vec4(colorRGBA.rgb*light,colorRGBA.a);
}