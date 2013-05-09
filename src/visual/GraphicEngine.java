package visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import Helper.Controller;
import Helper.Settings;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import logic.Bird;

public class GraphicEngine extends JFrame implements Observer{
	private static final long serialVersionUID = -1274754107343869256L;
	private float xPos;
    private float yPos;
    private BirdPanel birdPanel = new BirdPanel();
	private int width;
	private int height;
	private Controller controller;
	private Bird preyBird;

    public GraphicEngine(Controller cont){
    	this.controller = cont;
    	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				controller.closeProgramm();
			}
		});
		
    	Settings settings = Settings.getInstance();
    	width = settings.getPropertyAsInteger("WindowWidth");
    	height = settings.getPropertyAsInteger("WindowHeight");
        birdPanel = new BirdPanel();

        birdPanel.addMouseMotionListener(new MouseMotionHandler());
        
        this.setSize(width, height);
        this.add(birdPanel);
        this.pack();
        this.setVisible(true);
        
        
    }

	@SuppressWarnings("unchecked")
	public void update(Observable o, Object arg) {
		
		// so wird auf die Position jedes einzelnen Vogels zugegriffen;
		ArrayList<Bird> birds = (ArrayList<Bird>) arg;
		BirdShape birdShape;
		Bird bird;
		
		for (Iterator<Bird> iterator = birds.iterator(); iterator.hasNext();) {
			bird = (Bird) iterator.next();
			xPos = bird.getPosition().getX();
			yPos = bird.getPosition().getY();
			birdShape = bird.getBirdShape();
			birdShape.setPoint(xPos, yPos);
			birdShape.setOrientation(bird.getVelocity().getNormalizedVector());
		}
		
		// erneuere den Raubvogel:
		bird = preyBird;
		xPos = bird.getPosition().getX();
		yPos = bird.getPosition().getY();
		birdShape = bird.getBirdShape();
		birdShape.setPoint(xPos, yPos);
		birdShape.setOrientation(bird.getVelocity().getNormalizedVector());
		birdPanel.repaint();
		
		
	}

        public void setBirdFlock(ArrayList<Bird> birds) {
            ArrayList<BirdShape> shapeList = new ArrayList<BirdShape>();
            for (Bird bird : birds) {
                shapeList.add(bird.getBirdShape());
            }
            this.birdPanel.setBirdList(shapeList);
        }

        public void setPreyBird(Bird bird){
        	this.preyBird = bird;
        }
        private class BirdPanel extends JPanel{
			private static final long serialVersionUID = 5725072247094311057L;
			private ArrayList<BirdShape> birds;

            @Override public void paintComponent(Graphics g){
                super.paintComponent(g);
        		
                preyBird.getBirdShape().render(g);
                for (BirdShape birdShape : birds) {
                	
                	birdShape.render(g);

                }
                
            }

            @Override public Dimension getPreferredSize(){
                return new Dimension(width, height);
            }

            public void setBirdList(ArrayList<BirdShape> birdList){
                this.birds = birdList;
            }


        }
        
        private class MouseMotionHandler implements MouseMotionListener{

			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseMoved(MouseEvent e) {
				controller.setMousePosition(e.getPoint());
			}
        	
        }
}