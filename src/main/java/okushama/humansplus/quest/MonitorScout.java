package okushama.humansplus.quest;

import javax.script.ScriptException;

import okushama.humansplus.Human;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Chunk;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class MonitorScout {
	public MonitorScout(){
		
	}
	
	public String flags = "";
	public BiomeGenBase biome;
	
	public void resetVars(){
		flags = "";
	}
	
	public void setVarsToCurrentQuest(){
		flags = HandlerQuests.currentQuest.flags;
		for(BiomeGenBase b : BiomeGenBase.biomeList)
		if(b != null)
		if(b.biomeName.trim().toLowerCase().equals(flags.toLowerCase())){
			biome = b;
			continue;
		}
		if(getVendorBiome() != null){
			if(getVendorBiome() == biome){
				HandlerQuests.currentQuest.quitQuest();
			}
		}
	}
	
	public void onUpdate(){
		if(HandlerQuests.currentQuest != null){
			if(HandlerQuests.currentQuest.hasStarted && !HandlerQuests.currentQuest.requirementsMet){
				try {
					HandlerQuests.currentQuest.engine.eval("scout_update()");
				} catch (ScriptException e) {
					e.printStackTrace();
				}
				if(getVendorBiome() != null){
					if(getVendorBiome() == biome){
						HandlerQuests.currentQuest.meetRequirements();
						resetVars();
					}
				}
			}
			if(HandlerQuests.currentQuest.hasStarted && HandlerQuests.currentQuest.requirementsMet){
				try {
					HandlerQuests.currentQuest.engine.eval("scout_reqmet_update()");
				} catch (ScriptException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public BiomeGenBase getVendorBiome(){
		Human p = HandlerQuests.currentQuest.vendor;
		World w = p.worldObj;
		Chunk chunk = w.getChunkFromBlockCoords((int)p.posX, (int)p.posZ);
		return chunk.getBiomeGenForWorldCoords((int)Math.floor(p.posX) & 15, (int)Math.floor(p.posZ) & 15, Minecraft.getMinecraft().theWorld.getWorldChunkManager());
	}

}
