package okushama.humansplus.quest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import okushama.humansplus.Human;
import okushama.humansplus.HumanHunter;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.World;

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
		World w = Minecraft.getMinecraft().getIntegratedServer().getWorld(Minecraft.getMinecraft().player.dimension);
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
		w.spawnEntity(human);		
		return human;
	}
	
	public void setHeldItem(Human h, int itemID){
		if(h != null)
			if(Item.getItemById(itemID) != null)
				h.heldItem = new ItemStack(Item.getItemById(itemID));
	}
	
	public net.minecraft.pathfinding.Path pathFromTo(Entity e, Entity e2){
		// This method doesn't exist in the same way in 1.12.2, returning null for now
		return null;
	}
	
	public int distance(Entity ent, Entity ent2){
		return (int)ent.getDistance(ent2);
	}
	
	public void setDropItem(Human h, int itemID){
		if(h != null)
			if(Item.getItemById(itemID) != null)
				h.dropItem = new ItemStack(Item.getItemById(itemID));
	}

}
