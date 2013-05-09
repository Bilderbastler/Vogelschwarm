package visual;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import Helper.Settings;
import Helper.Vector2d;

/**
 *
 * @author florianneumeister
 */
public class BirdShape{
    private float x;
    private float y;
    private int width;
    private Color color;
	private Vector2d direction;
	private Polygon triangle;

    public BirdShape(Color c){
    	Settings settings = Settings.getInstance();
    	this.width = settings.getPropertyAsInteger("BirdImageSize");
    	this.color = c;
		//erzeuge Polygon mit drei Ecken
		int[] xPoints = {0, -1*width, width};
		int[] yPoints = { -2*width, width*2, width*2};
		triangle = new Polygon(xPoints, yPoints, 3);

    }

    public void setPoint(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setOrientation(Vector2d vector2d){
        this.direction = vector2d;
    }

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transformation = new AffineTransform();
		//rotiere den Vogel
		transformation.translate(x, y);
		try {
			double xOrient = (double)direction.getY();
			double yOrient = (double)direction.getX();
			double pos = Math.atan2(xOrient, yOrient)+Math.PI / 2;
			transformation.rotate(pos);
			g2d.setTransform(transformation);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		// zeichne das Dreieck
		g2d.setColor(color);
		g2d.fillPolygon(triangle);
		
	}
    
    
}