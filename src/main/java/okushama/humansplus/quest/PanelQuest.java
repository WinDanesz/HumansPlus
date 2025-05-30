package okushama.humansplus.quest;

import java.util.ArrayList;

import okushama.humansplus.Panel;

import org.lwjgl.input.Keyboard;

public class PanelQuest extends Panel {

	public Quest theQuest;
	public PanelQuest() {
		super(10, 10, 256, 86, "/okushama/humansplus/test.png");
	}
	
	public void setQuest(Quest q){
		theQuest = q;
		onInit();
	}
	
	public String u = "\u00A7n";
	public String r = "\u00A7r";

	@Override
	public void onInit() {
		if(theQuest == null){
			return;
		}
		setLines();
		this.setLinesPos(x+15, y+15);
	}
	
	public void setLines(){
		lines = new ArrayList<String>();
		addLine(ch+"l"+theQuest.name);
		String[] text = splitByLength(theQuest.getText(),38);
		for(String s : text){
			addLine(s);
		}
	}
	
	public String[] splitByLength(String s, int l) {
		ArrayList<String> result = new ArrayList<String>();
		char[] sAr = s.toCharArray();
		int start = 0;
		// start with
		for (int i = l; i < sAr.length; i++) {
			if (sAr[i] == ' ') {
				result.add(s.substring(start, i));
				start = i + 1;
				i += l;
			}
		}
		result.add(s.substring(start));
		String[] sresult = new String[4];
		for(int i = 0; i < result.size(); i++){
			sresult[i] = result.get(i);
		}
		return sresult;
	}
	
	@Override
	public void onUpdate(){

	}

}
