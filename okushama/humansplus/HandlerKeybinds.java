package okushama.humansplus;

import java.util.EnumSet;

import okushama.humansplus.quest.HandlerQuests;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class HandlerKeybinds extends KeyHandler{

	public Minecraft mc = Minecraft.getMinecraft();
	public static KeyBinding questKey = new KeyBinding("Humans+ Quest", Keyboard.KEY_H);
	
	public HandlerKeybinds() {
		super(new KeyBinding[]{questKey}, new boolean[]{false});
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Humans+ Key Bindings";
	}

	public static boolean showPanel = false;
	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
	//	System.out.println(kb.keyCode+" "+questKey.keyCode+" "+isRepeat);

		if(kb.keyCode == questKey.keyCode && tickEnd){
			
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

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.CLIENT);
	}
	

}
