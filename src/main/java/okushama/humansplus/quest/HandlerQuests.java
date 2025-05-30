package okushama.humansplus.quest;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.script.ScriptException;

import okushama.humansplus.HandlerKeybinds;
import okushama.humansplus.Human;
import okushama.humansplus.ModHumansPlus;
import okushama.humansplus.Panel;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class HandlerQuests {
	
	public Minecraft mc = Minecraft.getMinecraft();
	public ModHumansPlus humansMod = new ModHumansPlus();
	
	public static Quest currentQuest = null;
	public ArrayList<Quest> loadedQuests = new ArrayList<Quest>();
	public ArrayList<String> finishedQuests = new ArrayList<String>();
	
	public static void setQuest(String s){
		currentQuest = new Quest(s);
	}
	
	public HandlerQuests(){
		Panel p = new PanelQuest();
		Panel p1 = new PanelQuestImage(266, 17, 40, 40, "/okushama/humansplus/boots.png",0,0);
		panels.add(p);
		panels.add(p1);
		for(String f : new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()+"/okushama/humansplus/quest/").list()){
			if(f.endsWith("1.txt")){
				System.out.println("Loaded Quest! "+f);
				loadedQuests.add(new Quest("/okushama/humansplus/quest/"+f));
			}
		}
	}
	
	int finishedTimer = 100;
	boolean startFinishTimer = false;
	public static String nextQuest = null;
	public static Human nextVendor = null;
	
	public int timeout = 6800;
	public void onTick(){
		if(mc.world != null && mc.player != null){
			if(currentQuest != null){
				if(!currentQuest.hasStarted){
					timeout--;
					if(timeout == 0){
						currentQuest.quitQuest();
						timeout = 6800;
						System.out.println("Quest timed out!");
						return;
					}
				}else{
					timeout = 6800;
				}
				if(currentQuest.vendor != null){
					if(currentQuest.vendor.isDead){
						currentQuest.quitQuest();
						return;
					}
					if(currentQuest.vendor.getAttackTarget() instanceof EntityPlayer){
						currentQuest.quitQuest();
						return;
					}
					if(mc.player != null)
					if(currentQuest.vendor.getDistance(mc.player) < 10D){
						currentQuest.vendor.isInRangeToVend = true;
					}else{
						currentQuest.vendor.isInRangeToVend = false;
					}
					currentQuest.onUpdateDuringQuest();
					if(currentQuest == null){
						return;
					}
				}
				if(currentQuest.hasFinished){
					if(currentQuest.vendor == null){
						return;
					}
					if(!startFinishTimer){
						if(currentQuest.nextQuest != null){
							nextQuest = currentQuest.nextQuest;
							nextVendor = currentQuest.vendor;
						}
						if(currentQuest.vendor != null)
							currentQuest.vendor.setVendingQuest(false);
						finishedQuests.add(currentQuest.name);
						((PanelQuest)panels.get(0)).setLines();
						startFinishTimer = true;
						return;
					}else{
						finishedTimer--;
						if(finishedTimer == 0){
							currentQuest = null;
							finishedTimer = 100;
							startFinishTimer = false;
							return;
						}
					}
				}
				if(currentQuest.hasStarted){
					((PanelQuest)panels.get(0)).setLines();
					if(currentQuest.type == Quest.COLLECTION){
						
					}
				}
			}else{
				for(Quest q : loadedQuests){
					boolean finished = false;
					for(String fq : finishedQuests){
						/*if(q.name.equals(fq)){
							finished = true;
						}*/
					}
					if(finished){
						continue;
					}
					int rand = new Random().nextInt(300);
					if(rand == 0){
						if(nextQuest != null){
							System.out.println("Quest String!!!");
							this.setQuest(nextQuest);
							currentQuest.vendor = nextVendor;
							currentQuest.vendor.setVendingQuest(true);
							currentQuest.vendor.setName(currentQuest.npcname);
							((PanelQuest)panels.get(0)).setQuest(currentQuest);
							currentQuest.initQuest();
							nextQuest = null;
							nextVendor = null;
						}else{
							this.setQuest("/okushama/humansplus/quest/"+q.questConfig.getName());
							((PanelQuest)panels.get(0)).setQuest(currentQuest);
							currentQuest.initQuest();
							HandlerKeybinds.showPanel = true;
						//	currentQuest.startQuest();
							continue;
						}
					}
				}
			}
		}
	}
	
	public static ArrayList<Panel> panels = new ArrayList<Panel>();
	
	public String getQuestType(int i){
		String s = "";
		if(i == Quest.COLLECTION){
			s = "Collection";
		}
		if(i == Quest.SCOUT){
			s = "Scout";
		}
		if(i == Quest.HUNTED){
			s = "Hunted";
		}
		if(i == Quest.BOUNTY){
			s = "Bounty";
		}
		return s;
	}

	public void onRenderTick(){
		if(currentQuest != null){
			if(currentQuest.vendor != null && HandlerKeybinds.showPanel){
				if(currentQuest.vendor.isInRangeToVend){				for(Panel p : panels){
					p.onUpdate();
					if(!p.visible || mc.currentScreen != null || mc.player == null ){ continue; }
					GL11.glPushMatrix();
					GL11.glScalef(0.76F,  0.76F, 1F);
					GL11.glPushMatrix();
					mc.renderEngine.bindTexture(new net.minecraft.util.ResourceLocation(p.texture));	
						GL11.glEnable(GL11.GL_BLEND);
				        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				        GL11.glTranslatef((float)p.x, (float)p.y, 0F);
						mc.ingameGUI.drawTexturedModalRect(0, 0, 0, 0, p.w, p.h);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glPopMatrix();
						for(int i = 0; i < p.lines.size(); i++){
							String s = p.lines.get(i);
							mc.fontRenderer.drawString(s, p.linesX, p.linesY+i*13, 0xFFFFFF);
						}
						if(p instanceof PanelQuestImage){
							((PanelQuestImage)p).drawImageInside();
						}
						GL11.glPopMatrix();
						
					}
				}else{
					String[] key = 	 {"Quest Title",     "Quest Type",
					  "Quest Phase", "Vendor Pos"};
					String loc = (int)currentQuest.vendor.posX+" "+(int)currentQuest.vendor.posY+" "+(int)currentQuest.vendor.posZ;
					String[] value = {currentQuest.name, getQuestType(currentQuest.type),
					  currentQuest.getPhase(), loc};
					
					for(int i = 0; i < key.length; i++){
						String k = key[i];
						String v = value[i];
						GL11.glPushMatrix();
						GL11.glScalef(0.76F, 0.76F, 1F);
						mc.fontRenderer.drawString(k, 10, 10+i*13, 0xE8CF15);
						mc.fontRenderer.drawString("-", 80, 10+i*13, 0xE8CF15);
						mc.fontRenderer.drawString(v, 90, 10+i*13, 0xFFFFFF);
						GL11.glPopMatrix();
					}
				}
			}
		}
	}

}
