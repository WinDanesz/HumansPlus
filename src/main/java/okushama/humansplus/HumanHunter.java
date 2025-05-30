package okushama.humansplus;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.SoundEvent;

public class HumanHunter extends Human{

	public HumanHunter(World world) {
		super(world);
		name = "Hunter";
		alignment = GOOD;
		
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCreeper.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntitySpider.class, false));
	}

	@Override
	public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand)
    {
		return super.processInteract(player, hand);
	}
	
	boolean hasPlayed = false;
	int timeDelay = 60;
	boolean creeperWarning = false;
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if(!hasPlayed && this.getAttackTarget() != null && getAttackTarget().getDistance(this) > 5D){
			if(this.getAttackTarget() instanceof EntityPlayer){
				world.playSound(null, this.posX, this.posY, this.posZ, 
					net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("okushama.humansplus", "testing")), 
					net.minecraft.util.SoundCategory.NEUTRAL, 0.7F, 1F);
				hasPlayed = true;
			}
		}
		if(this.getAttackTarget() != null && getAttackTarget().getDistance(this) <= 5D){
			timeDelay--;
			if(timeDelay == 0)
				hasPlayed = false;
		}
		
		try{
			EntityPlayer player = world.getClosestPlayer(this.posX, this.posY, this.posZ, 30D, false);
			if(player != null && this.getDistance(player) < 30D){
				for(net.minecraft.entity.Entity ent : world.loadedEntityList){
					if(ent instanceof EntityCreeper){
						EntityCreeper crp = ((EntityCreeper)ent);
						if(crp.getAttackTarget() == player){
							if(!creeperWarning){
								world.playSound(null, this.posX, this.posY, this.posZ, 
									net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("okushama.humansplus", "huntercreeper")), 
									net.minecraft.util.SoundCategory.NEUTRAL, 1F, 1F);
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
	
	@Override
	protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn){
		return net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("okushama.humansplus", "testhurt"));
	}
	
	@Override
	protected net.minecraft.util.SoundEvent getDeathSound(){
		return net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("okushama.humansplus", "testdeath"));
	}
	
	
	
	
}
