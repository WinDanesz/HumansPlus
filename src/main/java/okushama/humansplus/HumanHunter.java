package okushama.humansplus;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class HumanHunter extends Human {

	public HumanHunter(World world) {
		super(world);
		name = "Hunter";
		alignment = GOOD;
		
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCreeper.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntitySpider.class, false));
	}

	@Override
	public void setTargetTypes(Class<? extends EntityLiving>[] mobs) {
		// Hunters target hostile mobs
	}
}

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
