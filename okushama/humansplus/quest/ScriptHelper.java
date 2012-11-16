package okushama.humansplus.quest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import okushama.humansplus.Human;
import okushama.humansplus.HumanHunter;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PathEntity;
import net.minecraft.src.World;

public class ScriptHelper {
	
	public Quest theQuest;
	public ScriptHelper(Quest q){
		theQuest = q;
	}
	
	public Human createHumanInWorld(String s, double x, double y, double z, boolean random){
		/*for(RegistryHuman.Human h : RegistryHuman.humans){
			if(h.name.toLowerCase().equals(s.toLowerCase())){
				try {
					World w = Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(Minecraft.getMinecraft().thePlayer.dimension);
					//if(w.isRemote) return null;
					Constructor c = h.entClass.getDeclaredConstructor(World.class);
					c.setAccessible(true);
					Human human = (Human)c.newInstance(Minecraft.getMinecraft().theWorld);
					if(!random){
					human.setLocationAndAngles(x, y, z, 180, 180);
					}else{
						Random rand = new Random();
						human.setLocationAndAngles(x+(rand.nextBoolean() ? 10+rand.nextInt(30) : -10-rand.nextInt(30)), y+3, z+(rand.nextBoolean() ? 10+rand.nextInt(30) : -10-rand.nextInt(30)), 180, 180);
					}
					Minecraft.getMinecraft().theWorld.spawnEntityInWorld(human);
					return human;
					
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		Human human = null;
		World w = Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(Minecraft.getMinecraft().thePlayer.dimension);
		if(s.toLowerCase().equals("hunter")){		
			human = (HumanHunter) new HumanHunter(w);
		}
		if(human == null){
			return null;
		}
		if(!random){
			human.setLocationAndAngles(x, y, z, 180, 180);
		}else{
			Random rand = new Random();
			human.setLocationAndAngles(x+(rand.nextBoolean() ? 10+rand.nextInt(30) : -10-rand.nextInt(30)), y+3, z+(rand.nextBoolean() ? 10+rand.nextInt(30) : -10-rand.nextInt(30)), 180, 180);
		}
		w.spawnEntityInWorld(human);		
		return human;
	}
	
	public void setHeldItem(Human h, int itemID){
		if(h != null)
			if(Item.itemsList[itemID] != null)
				h.heldItem = new ItemStack(Item.itemsList[itemID]);
	}
	
	public PathEntity pathFromTo(Entity e, Entity e2){
		return e.worldObj.getPathEntityToEntity(e, e2, 40F, true, false, false, true);
	}
	
	public int distance(Entity ent, Entity ent2){
		return (int)ent.getDistanceToEntity(ent2);
	}
	
	public void setDropItem(Human h, int itemID){
		if(h != null)
			if(Item.itemsList[itemID] != null)
				h.dropItem = new ItemStack(Item.itemsList[itemID]);
	}

}
