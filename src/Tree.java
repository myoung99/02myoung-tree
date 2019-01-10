import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

/*
 * @author Melecia Young
 * this class will render a tree given a graphics helper object
 */

public class Tree {
	
	//instance variables
	private double splitAngle;
	private int maxSegments;
	
	
	/*
	 * The constructor gets the splitAngle and maxSegs inputed from treeframe
	 * @param splitAngle -- the angle that the branch is going to split at
	 * @param maxSegments -- the max number of branch segments in the tree
	 * 
	 */
	
	public Tree(double splitAngle, int maxSegments) {
		this.splitAngle = splitAngle;
		this.maxSegments = maxSegments;
		
	}
	
	//getters
	public double getSplitAngle() {
		return splitAngle;
	}
	
	public int getMaxSegments() {
		return maxSegments;
	}
	
	// setters
	public void setSplitAngle(double splitAngle) {
		this.splitAngle = splitAngle;
	}
	
	public void setMaxSegments(int maxSegments) {
		this.maxSegments = maxSegments;
	}
	
	/*
	 * drawOn renders the tree with the help of g
	 * starts the recursive algorithm by calling drawTree using the current 
	 * parameter values
	 * 
	 * @param g -- graphics object
	 * @param x -- starting x endpoint of the tree
	 * @param y -- starting y endpoint of the tree
	 * @param angle -- splitAngle of the tree
	 * 
	 */
	
	public void drawOn(Graphics g, int x, int y, double angle) {
		drawTree(g,300,500,splitAngle,maxSegments);
	}
	
	/*
	 * drawTree is a private recursive algorithm, 
	 * if segRemaining is less than 4 draw leaves at the current sx, sy ,
	 * if segsREmaining is greater than 0 draw the next branch segment from sx,sy in the direction
	 * of sAngle and then draw 2 smaller trees sprouting at +/- split angle..
	 * 
	 *@param g 
	 *@param sx -- is the initial endpoint of the tree
	 *@param sy -- is the initial enpoint of the tree
	 *@param sAngle -- is the initial splitangle of the tree
	 *@param segsRemaining
	 */
	
	private void drawTree(Graphics g, int sx, int sy, double sAngle, int segsRemaining) {
		/*
		 * Set up the random noise algorithm for the angle
		 */
		
		Random r = new Random();
		final int ANGLE_MAX = 20;
		int angleNoise = r.nextInt(ANGLE_MAX);
		
		/*
		 * Change the stroke width of the tree's brnaches
		 */
		
		g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D) g; // get a better handle on the graphics object
		BasicStroke stroke = new BasicStroke(2); //line will be 15 pixels wide!
		g2.setStroke(stroke); // now the graphics system will use this stroke
		
		/*
		 * Generate the branches of the tree by multiplying the sx/sy by the sin/cos of
		 * the given angle in order to properly generate the branch at the correct angle.
		 */
		if (segsRemaining == 1) {
			return;
		} else {
			/*
			 * nx/ny takes the current sx/sy and computes the algorithm for the appropriate
			 * new endpoint
			 */
			int nx = (int)Math.round(sx + branchLength(segsRemaining) * Math.cos(Math.toRadians(-110 + sAngle)));
			int ny = (int)Math.round(sy + branchLength(segsRemaining) * Math.sin(Math.toRadians(-110 + sAngle)));
			
			g.drawLine(sx, sy, nx, ny);
			
			drawTree(g,nx,ny,sAngle + splitAngle + angleNoise, segsRemaining - 1);
			drawTree(g, nx, ny, sAngle - splitAngle + (-angleNoise), segsRemaining - 1);
			
			if(segsRemaining < 4) {
				drawLeaf((Graphics2D) g,nx,ny,splitAngle + sAngle);
				drawLeaf((Graphics2D) g,nx,ny,splitAngle + (sAngle-10));
				drawLeaf((Graphics2D) g,nx,ny,splitAngle + (sAngle+10));
			}
		}
		
		
	}
	
	/*
	 * drawLeaf is a private helper called by drawTree in order to draw one or more 
	 * leaves at a specified location (x,y) oriented by theta
	 * 
	 * @param g -- graphics object
	 * @param x -- position of the leaf
	 * @param y -- position of the leaf
	 * @param theta -- tilted angle of the leaf
	 */
	
	private void drawLeaf(Graphics g, int x, int y, double theta) {
		/*
		 * set up the random noise algorithm for the color
		 */
		
		Random r = new Random();
		final int COLOR_MAX = 120;
		int colorNoise = r.nextInt(COLOR_MAX) + 130;
		
		AffineTransform xf = ((Graphics2D) g).getTransform(); 
		g.translate(x, y); // move the coordinate origin to x,y
		((Graphics2D) g).rotate(theta); // rotate to align with the current growth orientation
		
		//now draw the leaf pattern as if you were drawing around an origin
		g.setColor(new Color(0,colorNoise,0));
		g.fillOval(5, 0, 10, 5);
		g.fillOval(-5, 0, 10, 5);
		g.setColor(Color.pink);
		g.fillOval(0, 0, 7, 7);
		
		
		((Graphics2D) g).setTransform(xf); // reset affine transform to the original state
	}
	
	/*
	 * branchLength computes and returns the length of the current branch to be
	 * rendered given the number of segments remaining
	 * 
	 * @param segsRemaining
	 * @return the length of the branch
	 */
	
	public int branchLength(int segsRemaining) {
		/*
		 * set up the random noise algorithm for the branch 
		 */
		Random r = new Random();
		final int MAX = 8;
		int len;
		
		/*
		 * only add noise to the segRemaining under 4
		 */
		if (segsRemaining  < 4) {
			len = (r.nextInt(10) + 1) * segsRemaining;
		} else {
			len = MAX * segsRemaining;
		}
		return len;
		
	}

}
