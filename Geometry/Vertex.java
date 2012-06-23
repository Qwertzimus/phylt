package Geometry;

/**
 * Beschreiben Sie hier die Klasse Vertex.
 * 
 * @author Sascha
 * @version 0.01
 */
import org.lwjgl.opengl.*;
public class Vertex 
{
    float[] x,y,z;
    int listId;
    float rotationX,rotationY,rotationZ;
    boolean isFinished;
    public Vertex(float x,float y,float z)
    {
        this.x=new float[4];
        this.y=new float[4];
        this.z=new float[4];
        this.x[0]=x;
        this.y[0]=y;
        this.z[0]=z;

    }

    public void set(int i,float x,float y,float z)
    {
        this.x[i]=x;
        this.y[i]=y;
        this.z[i]=z;
        if(i==2){
            isFinished=true;
        }
    }

    public float[] getVertices()
    {
        return new float[]{0.0f+x[0], 1.0f+y[0], 0.0f+z[0],-1.0f+x[1], -1.0f+y[1], -1.0f+z[1],-1.0f+x[2], -1.0f+y[2], 1.0f+z[2]
        ,0.0f+x[0], 1.0f+y[0], 0.0f+z[0],-1.0f+x[1], -1.0f+y[1], 1.0f+z[1],1.0f+x[2], -1.0f+y[2], 1.0f+z[2]
        ,0.0f+x[0], 1.0f+y[0], 0.0f+z[0],1.0f+x[1], -1.0f+y[1], 1.0f+z[1],1.0f+x[2], -1.0f+y[2], -1.0f+z[2]
        ,0.0f+x[0], 1.0f+y[0], 0.0f+z[0],1.0f+x[1], -1.0f+y[1], -1.0f+z[1],-1.0f+x[2], -1.0f+y[2], -1.0f+z[2]
        };
    }

    public float[] getVerticesAndColors()
    {
        return new float[]{0.0f+x[0], 1.0f+y[0], 0.0f+z[0],-1.0f+x[1], -1.0f+y[1], -1.0f+z[1],-1.0f+x[2], -1.0f+y[2], 1.0f+z[2]
        ,0.0f+x[0], 1.0f+y[0], 0.0f+z[0],-1.0f+x[1], -1.0f+y[1], 1.0f+z[1],1.0f+x[2], -1.0f+y[2], 1.0f+z[2]
        ,0.0f+x[0], 1.0f+y[0], 0.0f+z[0],1.0f+x[1], -1.0f+y[1], 1.0f+z[1],1.0f+x[2], -1.0f+y[2], -1.0f+z[2]
        ,0.0f+x[0], 1.0f+y[0], 0.0f+z[0],1.0f+x[1], -1.0f+y[1], -1.0f+z[1],-1.0f+x[2], -1.0f+y[2], -1.0f+z[2]
        };
    }

    public void update()
    {
        rotationX+=2f;
        rotationY+=2f;
        rotationZ+=2f;
        double r=Math.random()*0.1f;
        x[3]+=r;
        z[3]+=r;
    }

    public void draw()
    {
        if(isFinished){
            GL11.glPushMatrix();
            //GL11.glTranslatef(x[3],y[0],z[3]);
            GL11.glRotatef(rotationY,0.0f,1.0f,0.0f);
            GL11.glRotatef(rotationZ,0.0f,0.0f,1.0f);
            rotationX+=2f;
            rotationY+=2f;
            rotationZ+=2f;
            double r=Math.random()*0.1f;
            x[3]+=r;
            z[3]+=r;
            GL11.glBegin(GL11.GL_TRIANGLES);

            GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
            GL11.glVertex3f(0.0f+x[0], 1.0f+y[0], 0.0f+z[0]); // Top Of Triangle (Left)
            GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
            GL11.glVertex3f(-1.0f+x[1], -1.0f+y[1], -1.0f+z[1]); // Left Of Triangle (Left)
            GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
            GL11.glVertex3f(-1.0f+x[2], -1.0f+y[2], 1.0f+z[2]); // Right Of Triangle (Left)

            GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
            GL11.glVertex3f(0.0f+x[0], 1.0f+y[0], 0.0f+z[0]); // Top Of Triangle (Front)
            GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
            GL11.glVertex3f(-1.0f+x[1], -1.0f+y[1], 1.0f+z[1]); // Left Of Triangle (Front)
            GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
            GL11.glVertex3f(1.0f+x[2], -1.0f+y[2], 1.0f+z[2]); // Right Of Triangle (Front)

            GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
            GL11.glVertex3f(0.0f+x[0], 1.0f+y[0], 0.0f+z[0]); // Top Of Triangle (Right)
            GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
            GL11.glVertex3f(1.0f+x[1], -1.0f+y[1], 1.0f+z[1]); // Left Of Triangle (Right)
            GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
            GL11.glVertex3f(1.0f+x[2], -1.0f+y[2], -1.0f+z[2]); // Right Of Triangle (Right)

            GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
            GL11.glVertex3f(0.0f+x[0], 1.0f+y[0], 0.0f+z[0]); // Top Of Triangle (Back)
            GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
            GL11.glVertex3f(1.0f+x[1], -1.0f+y[1], -1.0f+z[1]); // Left Of Triangle (Back)
            GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
            GL11.glVertex3f(-1.0f+x[2], -1.0f+y[2], -1.0f+z[2]); // Right Of Triangle (Back)
            GL11.glEnd();
            GL11.glPopMatrix();
        }
    }
}
