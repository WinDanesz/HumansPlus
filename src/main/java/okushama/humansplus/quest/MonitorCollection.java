package okushama.humansplus.quest;

import java.util.*;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;

public class MonitorCollection {
	
	
	public ArrayList<Integer> itemsToCollect = new ArrayList<Integer>();
	public HashMap<Integer, Integer> amountToCollect = new HashMap<Integer, Integer>();
	public String flag = "";
	
	public MonitorCollection(){
		
	}
	
	public void setVarsToCurrentQuest(){
		flag = HandlerQuests.currentQuest.flags;
		if(!flag.contains(";")){
			flag = flag+";";
		}
		String[] flagss = flag.split(";");
		for(int i = 0; i < flagss.length; i++){
			if(!flagss[i].contains("\\*")){
				flagss[i] = flagss[i]+"*1";
			}
			String[] fl = flagss[i].split("\\*");
				itemsToCollect.add(Integer.parseInt(fl[0]));
				amountToCollect.put(itemsToCollect.get(i), Integer.parseInt(fl[1]));
		}
	}
	
	public void resetVars(){
		itemsToCollect = new ArrayList<Integer>();
		amountToCollect = new HashMap<Integer, Integer>();
		flag = "";
		timesTriedOnFull = 0;
	}
	
	public int timesTriedOnFull = 0;
	
	public void onInteractWithVendor(EntityPlayer p){
		try{
		int tempAmt = itemsToCollect.size();
		//System.out.println("Collection is interacting with a vendor/player event!");
		for(int i = 0; i < itemsToCollect.size(); i++){
		//	System.out.println("Item to collect: "+Item.itemsList[itemsToCollect.get(i)].getItemName());
			if(invHasEnough(p.inventory, itemsToCollect.get(i), amountToCollect.get(itemsToCollect.get(i)), false)){
				tempAmt--;
			}
		}
		if(tempAmt == 0){
			//System.out.println("Necessary items!");
			if(p.inventory.getFirstEmptyStack() < 0){
				if(timesTriedOnFull == 0)
				p.addChatMessage("You do not have enough free space to accept the reward!");
				if(timesTriedOnFull == 1)
					p.addChatMessage("Do not test my patience, As I said, you need space!");
				if(timesTriedOnFull == 2)
					p.addChatMessage("Last chance, make space for it, or we're through here.");
				if(timesTriedOnFull == 3){
					p.addChatMessage("You blew it, I no longer require your help!");
					HandlerQuests.currentQuest.quitQuest();
					resetVars();
				}
				timesTriedOnFull++;
				return;
			}
			HandlerQuests.currentQuest.meetRequirements();
			HandlerQuests.currentQuest.completeQuest();
			for(int i : itemsToCollect)
				invHasEnough(p.inventory, i, amountToCollect.get(i), true);
			resetVars();
		}
		}catch(Exception e){}
	}
	
	public boolean invHasEnough(InventoryPlayer inv, int id, int amount, boolean shouldTake){
		if(inv != null){
			//System.out.println("Checking inventory for itemid:"+id+" at amount "+amount+", take:"+shouldTake);
			int tempAmt = amount;
			for(int i = 0; i < inv.mainInventory.length; i++){
				if(inv.mainInventory[i] != null){
				//	System.out.println(inv.mainInventory[i].itemID+"*"+inv.mainInventory[i].stackSize+" is in slot "+i);
					if(inv.mainInventory[i].itemID == id){
					//	System.out.println("That's an item we're looking for!");
						for(int k =0; k < amount; k++){
						if(inv.mainInventory[i].stackSize > 0){
							if(shouldTake){
							//	System.out.println("Deducting item! "+Item.itemsList[id].getItemName());
								if(inv.mainInventory[i].stackSize == 1){
									inv.mainInventory[i] = null;
									continue;
								}else{
								inv.mainInventory[i].stackSize--;
								}
								
							}
							tempAmt--;
						}
						//System.out.println("There's still "+tempAmt+" left to take of this item");
						}
					}
				}
			}
			if(tempAmt <= 0){
			//	System.out.println("Found everything required to pass true!");
				return true;
			}
		}
		
	//	System.out.println("Did not have enough to pass!");
		
		return false;
	}

}
