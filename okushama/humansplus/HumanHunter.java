package okushama.humansplus;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIBreakDoor;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAIMoveThroughVillage;
import net.minecraft.src.EntityAIMoveTwardsRestriction;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.World;

public class HumanHunter extends Human{

	public HumanHunter(World par1World) {
		super(par1World);
		name = "Hunter";
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
    {
		return super.interact(par1EntityPlayer);
	}
	
	boolean hasPlayed = false;
	int timeDelay = 60;
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if(!hasPlayed && this.entityToAttack != null && entityToAttack.getDistanceToEntity(this) > 5D){
			if(this.entityToAttack instanceof EntityPlayer){
				worldObj.playSoundAtEntity(this, "okushama.humansplus.testing", 0.7F, 1F);
				hasPlayed = true;
			}
		}
		if(this.entityToAttack != null && entityToAttack.getDistanceToEntity(this) <= 5D){
			timeDelay--;
			if(timeDelay == 0)
			hasPlayed = false;
		}
		try{
		if(this.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < 30D){
			for(int i = 0; i < worldObj.loadedEntityList.size(); i++){
				Entity ent = (Entity) worldObj.loadedEntityList.get(i);
				if(ent instanceof EntityCreeper){
					EntityCreeper crp = ((EntityCreeper)ent);
					if(crp.getEntityToAttack() == Minecraft.getMinecraft().thePlayer){
						if(!creeperWarning){
							worldObj.playSoundAtEntity(this, "okushama.humansplus.huntercreeper", 1F, 1F);
							creeperWarning = true;
						}
					}
				}
			}
		}else{
			creeperWarning = false;
		}
		}catch(Exception e){}
	}
	
	public boolean creeperWarning = false;
	
	@Override
	public String getHurtSound(){
		return "okushama.humansplus.testhurt";
	}
	
	@Override
	public String getDeathSound(){
		return "okushama.humansplus.testdeath";
	}
	
	
	
	
}
