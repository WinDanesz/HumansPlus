package okushama.humansplus;

import java.util.HashMap;
import java.util.Random;

public class RegistryTextures {
	
	private static HashMap<Class<? extends Human>, String> textures = new HashMap<Class<? extends Human>, String>();
	
	private static String dir = "/okushama/humansplus/mobs/";
	static{
		textures.put(HumanHunter.class, "hunter1.png;hunter2.png;hunter3.png;hunter4.png");
		textures.put(HumanRogue.class, "Rogue1.png;Rogue2.png;Rogue3.png;Rogue4.png;Rogue5.png;Rogue6.png");
		textures.put(HumanSamurai.class, "samurai1.png;samurai2.png;samurai3.png;samurai4.png;samurai5.png");
		textures.put(HumanBandit.class, "Bandit1.png;Bandit2.png;Bandit3.png");
	}
	
	public static String getTexture(Human h){
		if(textures.get(h.getClass()) != null){
			String s = textures.get(h.getClass());
			if(s.contains(";")){
				String[] split = s.split(";");
				Random rand = new Random();
				String result = split[rand.nextInt(split.length)];
			//	System.out.println(result);
				return dir+result;
			}else{
				return dir+s;
			}
		}
		return "/mob/char.png";
	}

}
