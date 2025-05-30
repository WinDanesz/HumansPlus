package okushama.humansplus;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandlerEvent {
	
	@SubscribeEvent
	public void onPlayerPickupItem(EntityItemPickupEvent evt){
		EntityPlayer p = evt.getEntityPlayer();
		EntityItem it = evt.getItem();
		
	}
	
	@SubscribeEvent
	public void itemToss(ItemTossEvent evt){
		/*Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("Upon attempting to drop the item, you can't let go.");
		evt.getPlayer().inventory.setInventorySlotContents(evt.getPlayer().inventory.currentItem, evt.getEntityItem().getItem());
		evt.getEntityItem().setDead();*/
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void itemExpire(ItemExpireEvent evt){
		EntityItem item = evt.getEntityItem();
		if(item.getItem().getItem() instanceof Sword){
			Sword sword = (Sword)item.getItem().getItem();
			if(sword.getRarity(item.getItem()) == RegistrySword.legendary)
				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("A Legendary Sword just expired!");
		}
	}
	

}
