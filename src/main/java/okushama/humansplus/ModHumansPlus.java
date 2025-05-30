package okushama.humansplus;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModHumansPlus.MODID, name = "Humans+", version = "4.0.0", acceptedMinecraftVersions = "[1.12,1.12.2]")
public class ModHumansPlus {
	
	public static final String MODID = "humansplus";
	
	@Instance(MODID)
	public static ModHumansPlus instance;
	
	@SidedProxy(clientSide = "okushama.humansplus.ClientProxy", serverSide = "okushama.humansplus.CommonProxy")
	public static CommonProxy proxy;
	
	public static RegistrySword swords = new RegistrySword(); 
	public static RegistryHuman humanRegistry;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		humanRegistry = new RegistryHuman();
		
		// Hook Events
		MinecraftForge.EVENT_BUS.register(new HandlerEvent());
		
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	

}