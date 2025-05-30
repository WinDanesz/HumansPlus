package okushama.humansplus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.EntityLiving;

public class Alignment {
	
	public static Class[] GOOD = {   HumanHunter.class, HumanSamurai.class, 
								     EntityIronGolem.class};
	
	public static Class[] EVIL = {   HumanRogue.class, HumanBandit.class};
	
	public static Class[] UNDEAD = { EntityZombie.class, EntitySpider.class, 
									 EntitySkeleton.class, EntityEnderman.class,
									 EntitySilverfish.class, EntityWitch.class};
	
	public static Class[] NEUTRAL = {EntityPlayer.class, EntityVillager.class};
	
	public static Class[] getAlignment(Class c){
		for(Class c2 : GOOD){
			if(c2 == c){
				return GOOD;
			}
		}
		for(Class c2 : EVIL){
			if(c2 == c){
				return EVIL;
			}
		}
		for(Class c2 : UNDEAD){
			if(c2 == c){
				return UNDEAD;
			}
		}
		for(Class c2 : NEUTRAL){
			if(c2 == c){
				return NEUTRAL;
			}
		}
		return new Class[]{};
	}
	public static Class[][] getTargets(Class c){
		for(Class c2 : GOOD){
			if(c2 == c){
				return new Class[][]{EVIL, UNDEAD};
			}
		}
		for(Class c2 : EVIL){
			if(c2 == c){
				return new Class[][]{GOOD, UNDEAD};
			}
		}
		for(Class c2 : UNDEAD){
			if(c2 == c){
				return new Class[][]{GOOD, EVIL, NEUTRAL};
			}
		}
		for(Class c2 : NEUTRAL){
			if(c2 == c){
				return new Class[][]{};
			}
		}
		return new Class[][]{};
	}

}
