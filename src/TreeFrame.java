/*
 * @author Melecia Young
 * creates a frame for the tree to be displayed in
 */
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class TreeFrame extends JFrame{
	
	//instance variables
	private Tree tree;
	private final int SIZE = 600;
	
	//constructor
	public TreeFrame () {
		super();
		
		tree = new Tree(20,9);
		this.setBackground(Color.cyan);
		this.setSize(SIZE,SIZE); //(600 * 600)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/*
	 * override paint method to paint the tree
	 * since its recursive it will basically tell the tree to paint itself
	 * 
	 */
	
	public void paint(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(500, 50, 70, 70);
		g.setColor(new Color(16,110,22)); // picked a nice color for the grass
		g.fillRect(0, 500, 600, 500);
		tree.drawOn(g, 250, 250, 200);
	}

}
