package okushama.humansplus;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding questKey;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		
		// Register key bindings
		questKey = new KeyBinding("key.humansplus.quest", KeyConflictContext.IN_GAME, Keyboard.KEY_H, "key.categories.humansplus");
		ClientRegistry.registerKeyBinding(questKey);
		
		// Register entity renderers
		RenderingRegistry.registerEntityRenderingHandler(HumanRogue.class, RenderHuman::new);
		RenderingRegistry.registerEntityRenderingHandler(HumanHunter.class, RenderHuman::new);
		RenderingRegistry.registerEntityRenderingHandler(HumanSamurai.class, RenderHuman::new);
		RenderingRegistry.registerEntityRenderingHandler(HumanBandit.class, RenderHuman::new);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		// Register client-side event handlers
		MinecraftForge.EVENT_BUS.register(new HandlerKeybinds());
		MinecraftForge.EVENT_BUS.register(new HandlerLogic());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
