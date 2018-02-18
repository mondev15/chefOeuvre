package view;

import model.Plane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public final class Radar extends JPanel {	

	private final List<Plane> planeList;
	
	public Radar(){		
		planeList = new ArrayList<>();
	}
	
	public void addPlaneToRadar(Plane p){
		planeList.add(p);
	}
	
	public void radarScan(){
		for (Plane p : planeList) {
			p.movePlane();
		}					
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {	
		
		Graphics2D g2d = (Graphics2D)g;
                //---
                g2d.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());		
		
		g2d.setColor(Color.GREEN);
                //---
		//g2d.drawArc(20, 20, 360,360,0, 180);
                g2d.drawOval(20, 20, 360,360);
		//---
		g2d.drawLine(200, 20, 200, 380);
		g2d.drawLine(20, 200, 380, 200);
		
		for (Plane p : planeList) {
			p.drawPlane(g2d);
                }
				
	}
        
        public List<Plane> getPlaneList(){
          return planeList;
        }
        
        
}
