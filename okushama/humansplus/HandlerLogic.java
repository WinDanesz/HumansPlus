package okushama.humansplus;

import java.util.ArrayList;
import java.util.EnumSet;

import okushama.humansplus.quest.HandlerQuests;

import org.lwjgl.opengl.GL11;


import net.minecraft.client.Minecraft;
import net.minecraft.src.Enchantment;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Tessellator;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class HandlerLogic implements ITickHandler{
	
	
	public HandlerLogic(){
		
	}

	public Minecraft mc = Minecraft.getMinecraft();
	
	public HandlerQuests questHandler = new HandlerQuests();
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.CLIENT))){
			onTick();
			questHandler.onTick();
		}
		if(type.equals(EnumSet.of(TickType.RENDER))){
			onRenderTick();
			questHandler.onRenderTick();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "Humans+ Update Handler";
	}
	
	public void onTick(){
		try{
			if(mc.thePlayer != null){
				if(mc.thePlayer.getCurrentEquippedItem() != null){
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	
	

	public void onRenderTick(){
		if(!HandlerKeybinds.showPanel){
		}else{
			
		}
		
	}
}
