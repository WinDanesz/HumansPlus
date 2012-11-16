package okushama.humansplus;

import java.util.ArrayList;


import net.minecraft.src.*;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;

@Mod(modid = "humansPlus", name = "Humans+", version = "3.0b")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class ModHumansPlus {
	
	@Instance("humansPlus")
	public static ModHumansPlus instance;
	public static RegistrySword swords = new RegistrySword(); 
	public static RegistryHuman humanRegistry;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Init
	public void init(FMLInitializationEvent event) {
		humanRegistry = new RegistryHuman();
		
		// Update 
		TickRegistry.registerTickHandler(new HandlerLogic(), Side.CLIENT);
		
		// Keybinds
		KeyBindingRegistry.registerKeyBinding(new HandlerKeybinds());
		
		// Hook Events
		MinecraftForge.EVENT_BUS.register(new HandlerEvent());
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event) {
		
	}

	

}