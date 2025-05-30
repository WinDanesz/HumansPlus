package okushama.humansplus;

import okushama.humansplus.quest.HandlerQuests;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HandlerLogic {
	
	public Minecraft mc = Minecraft.getMinecraft();
	public HandlerQuests questHandler = new HandlerQuests();
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			onTick();
			questHandler.onTick();
		}
	}
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			onRenderTickInternal();
			questHandler.onRenderTick();
		}
	}
	
	public void onTick(){
		try{
			if(mc.player != null){
				if(mc.player.getHeldItemMainhand() != null){
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void onRenderTickInternal(){
		if(!HandlerKeybinds.showPanel){
		}else{
			
		}
	}
}
