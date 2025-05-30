package okushama.humansplus.quest;

import javax.script.ScriptException;

import okushama.humansplus.Human;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.math.BlockPos;

import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MonitorScout {
	public MonitorScout(){
		
	}
	
	public String flags = "";
	public Biome biome;
	
	public void resetVars(){
		flags = "";
	}
	
	public void setVarsToCurrentQuest(){
		flags = HandlerQuests.currentQuest.flags;
		for(Biome b : Biome.REGISTRY){
			if(b != null)
				if(b.getBiomeName().trim().toLowerCase().equals(flags.toLowerCase())){
					biome = b;
					continue;
				}
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
	
	public Biome getVendorBiome(){
		Human p = HandlerQuests.currentQuest.vendor;
		World w = p.world;
		net.minecraft.util.math.BlockPos pos = new net.minecraft.util.math.BlockPos((int)p.posX, (int)p.posY, (int)p.posZ);
		Chunk chunk = w.getChunkFromChunkCoords((int)p.posX >> 4, (int)p.posZ >> 4);
		return chunk.getBiome(pos, w.getBiomeProvider());
	}

}
