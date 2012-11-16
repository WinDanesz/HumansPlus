package okushama.humansplus;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.*;

public class HandlerEvent {
	
	
	@ForgeSubscribe
	public void onPlayerPickupItem(EntityItemPickupEvent evt){
		EntityPlayer p = evt.entityPlayer;
		EntityItem it = evt.item;
		
	}
	

	@ForgeSubscribe
	public void itemToss(ItemTossEvent evt){
		/*Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("Upon attempting to drop the item, you can't let go.");
		evt.player.inventory.setInventorySlotContents(evt.player.inventory.currentItem, evt.entityItem.item);
		evt.entityItem.setDead();*/
	}
	
	@ForgeSubscribe
	public void itemExpire(ItemExpireEvent evt){
		EntityItem item = evt.entityItem;
		if(item.item.getItem() instanceof Sword){
			Sword sword = (Sword)item.item.getItem();
			if(sword.getRarity(item.item) == RegistrySword.legendary)
				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("A Legendary Sword just expired!");
		}
	}
	

}
