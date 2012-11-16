package okushama.humansplus;

import java.util.ArrayList;

public abstract class Panel {
	
	public int x, y, w, h, linesX, linesY;
	public String texture;
	
	public ArrayList<String> lines = new ArrayList<String>();
	
	
	public boolean visible = true;
	public Panel(int xx, int yy, int ww, int hh, String tt){
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		texture = tt;
		linesX = x;
		linesY = y;
		onInit();
	}
	
	public String ch = "\u00A7";
	
	public void addLine(String line){
		lines.add(line);
	}
	
	public void setLinesPos(int x, int y){
		linesX = x;
		linesY = y;
	}
	
	public void onUpdate(){
		
	}
	
	public abstract void onInit();
}
