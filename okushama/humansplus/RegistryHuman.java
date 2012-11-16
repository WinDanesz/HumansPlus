package okushama.humansplus;

import java.util.ArrayList;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderBiped;


public class RegistryHuman {

	public static ArrayList<Human> humans = new ArrayList<Human>();
	
	public RegistryHuman(){
		humans.add(new Human("Rogue", HumanRogue.class));
		humans.add(new Human("Hunter", HumanHunter.class));
		humans.add(new Human("Samurai", HumanSamurai.class));
		humans.add(new Human("Bandit", HumanBandit.class));
		
		for(Human h : humans){	
			// Registering an entity
			LanguageRegistry.instance().addStringLocalization("entity.humansPlus."+h.name+".name", "en_US", h.name);
			EntityRegistry.registerModEntity(h.entClass, h.name, ModLoader.getUniqueEntityId(), ModHumansPlus.instance, 500, 5, true);
			// EntityRegistry.registerGlobalEntityID(h.entClass, h.name, ModLoader.getUniqueEntityId(), 0x440077, 0xaa00dd);			
	        // Adding Spawn
	        if(h.biome != null){
	        	ModLoader.addSpawn(h.entClass, 7, 1, 4, EnumCreatureType.creature, h.biome);
	        }else{
	        	ModLoader.addSpawn(h.entClass, 7, 1, 4, EnumCreatureType.creature);     
	        }
	        // Setting Render
			RenderingRegistry.registerEntityRenderingHandler(h.entClass, new RenderHuman()); 		
		}
	}
	
	
	public static class Human{
		public String name;
		public Class<? extends EntityLiving> entClass;
		public Human(String n, Class c){
			name = n;
			entClass = c;
		}
		
		public Human(String n, Class c, BiomeGenBase... b){
			this(n,c);
			biome = b;
		}
		
		public BiomeGenBase[] biome = null;
		
	}
}


