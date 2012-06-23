package Physics;

/**
 * Beschreiben Sie hier die Klasse RayCast.
 * 
 * @author Sascha
 * @version 0.01
 */
import org.lwjgl.util.vector.*;
public class RayCast
{
    public static Vector3f RayCast(Vector3f origin,Vector3f direction,float length)
    {
        float x=origin.x+(float)Math.sin(Math.toRadians(direction.y))*length;
        float y=origin.y-(float)Math.sin(Math.toRadians(direction.x))*length;
        float z=origin.z-(float)Math.cos(Math.toRadians(direction.y))*length;
        return new Vector3f(x,y,z);
    }

}
