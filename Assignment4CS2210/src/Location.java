
public class Location {
	private int x;
	private int y;
	
	public Location (int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int xCoord() {
		return x;
	}
	
	public int yCoord() {
		return y;
	}
	
	public int compareTo(Location p) {
		// (x, y) < (x', y') this<p
		if ((this.x<p.xCoord())||((this.x==p.xCoord())&&(this.y<p.yCoord()))){
			return -1;
		}
		// (x, y) == (x', y') this=p
		else if ((this.x==p.xCoord())&& (this.y==p.yCoord())){
			return 0;
		}
		// (x, y) > (x', y') this>p
		return 1;
	}

}
