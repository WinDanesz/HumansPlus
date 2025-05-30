package okushama.humansplus;

import okushama.humansplus.quest.HandlerQuests;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HandlerKeybinds {

	public Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean showPanel = false;
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(ClientProxy.questKey.isPressed()) {
			
			if(HandlerQuests.currentQuest == null){
			//	HandlerQuests.setQuest("okushama/humansplus/tquest2.txt");
				//HandlerQuests.currentQuest.initQuest();
			}else{
				showPanel = !showPanel;
				if(!HandlerQuests.currentQuest.hasStarted){
					//HandlerQuests.currentQuest.startQuest();
					//System.out.println("Started Quest!");
				}else{
				//	HandlerQuests.currentQuest.hasFinished = true;
				}
			}
		}
	}
}
