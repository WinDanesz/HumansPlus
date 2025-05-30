package okushama.humansplus;

import java.util.ArrayList;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RegistryHuman {

	public static ArrayList<HumanEntry> humans = new ArrayList<HumanEntry>();
	
	public RegistryHuman(){
		humans.add(new HumanEntry("Rogue", HumanRogue.class));
		humans.add(new HumanEntry("Hunter", HumanHunter.class));
		humans.add(new HumanEntry("Samurai", HumanSamurai.class));
		humans.add(new HumanEntry("Bandit", HumanBandit.class));
		
		int entityId = 0;
		for(HumanEntry h : humans){	
			// Registering an entity
			EntityRegistry.registerModEntity(new ResourceLocation(ModHumansPlus.MODID, h.name.toLowerCase()), 
				h.entClass, h.name, entityId++, ModHumansPlus.instance, 80, 3, true);
			
	        // Adding Spawn
	        if(h.biome != null){
	        	for(Biome biome : h.biome) {
	        		EntityRegistry.addSpawn(h.entClass, 7, 1, 4, EnumCreatureType.CREATURE, biome);
	        	}
	        }else{
	        	// Add to all biomes
	        	for(Biome biome : ForgeRegistries.BIOMES.getValues()) {
	        		EntityRegistry.addSpawn(h.entClass, 7, 1, 4, EnumCreatureType.CREATURE, biome);
	        	}
	        }
	        
	        // Client-side rendering will be handled in ClientProxy
		}
	}
	
	public static class HumanEntry{
		public String name;
		public Class<? extends EntityLiving> entClass;
		
		public HumanEntry(String n, Class c){
			name = n;
			entClass = c;
		}
		
		public HumanEntry(String n, Class c, Biome... b){
			this(n,c);
			biome = b;
		}
		
		public Biome[] biome = null;
	}
}


