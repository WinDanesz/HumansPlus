package okushama.humansplus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import okushama.humansplus.quest.HandlerQuests;
import okushama.humansplus.quest.Quest;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.src.*;
import net.minecraftforge.common.EnumHelper;

public abstract class Human extends EntityMob implements IMob,
		IEntityAdditionalSpawnData {
	
	public int alignment = 0;
	public static int GOOD = 1, EVIL = -1;


	public Human(World par1World) {
		super(par1World);
		this.preventEntitySpawning = false;
		health = 20;
		texture = "";
		setPotentialHeldItems();
		heldItem = null;
		dropItem = null;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIBreakDoor(this));
		this.tasks.addTask(3, new EntityAIMoveTwardsRestriction(this,
				this.moveSpeed));
		this.tasks.addTask(4, new EntityAIMoveThroughVillage(this,
				this.moveSpeed, false));
		this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(6, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		initAlignment();
	}
	
	public void initAlignment(){
		for(Class[] alg : Alignment.getTargets(this.getClass())){
			for(int i =  0; i < alg.length; i++){
				targetTypes.add(alg[i]);
			}
		}
	}

	public ArrayList<Item> potentialHeldItems = new ArrayList<Item>();
	public ItemStack heldItem;
	public String name = "Human";
	public boolean isVending = false;
	public boolean isInRangeToVend = false;
	public float moveSpeed = 1F;

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(10, String.valueOf(this.name));
		this.dataWatcher.addObject(11,
				Byte.valueOf((byte) (this.isVending ? 1 : 0)));
	}

	public void setVendingQuest(boolean b) {
		isVending = b;
		this.dataWatcher.updateObject(11,
				Byte.valueOf((byte) (this.isVending ? 1 : 0)));
	}

	@Override
	public boolean interact(EntityPlayer p) {
		if (HandlerQuests.currentQuest != null) {
			if (!HandlerQuests.currentQuest.hasStarted && this.isVending) {
				HandlerQuests.currentQuest.startQuest();
				return true;
			}
			if (HandlerQuests.currentQuest.type == Quest.COLLECTION
					&& this.isVending) {
				HandlerQuests.currentQuest.collection.onInteractWithVendor(p);
				return true;
			}
			if (HandlerQuests.currentQuest.requirementsMet && this.isVending) {
				HandlerQuests.currentQuest.completeQuest();
			}
		}
		return true;
	}

	public void setName(String s) {
		name = s;
		this.dataWatcher.updateObject(10, String.valueOf(this.name));
	}

	public void setPotentialHeldItems() {
		potentialHeldItems.add(ModHumansPlus.swords.swords.get(4).theSword);
	}

	public ArrayList<Class> targetTypes = new ArrayList<Class>();

	public void setTargetTypes(Class<? extends EntityLiving>[] mobs) {

	}

	@Override
	protected Entity findPlayerToAttack() {
		EntityLiving var1 = getClosestEntity(targetTypes, posX, posY, posZ, 40D);
		if (var1 == null) {
			return null;
		}
		for (Class<? extends EntityLiving> c : targetTypes) {
			// System.out.println(var1.getClass().getName());
			if (var1.getClass() == c) {
				// System.out.println("Found a Spider!");
				return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
			}
		}
		return null;
	}

	public EntityLiving getClosestEntity(ArrayList<Class> type, double par1,
			double par3, double par5, double par7) {
		double var9 = -1.0D;
		EntityLiving var11 = null;

		for (int var12 = 0; var12 < worldObj.getLoadedEntityList().size(); ++var12) {
			if (!(worldObj.getLoadedEntityList().get(var12) instanceof EntityLiving)) {
				continue;
			}
			EntityLiving var13 = (EntityLiving) worldObj.getLoadedEntityList()
					.get(var12);
			boolean isType = false;
			if (var13 == this) {
				continue;
			}
			for (Class c : type) {
				if (c != var13.getClass()) {
					continue;
				} else {
					if (rand.nextInt(4) != 0)
						isType = true;
				}

			}
			if (!isType) {
				continue;
			}
			double var14 = var13.getDistanceSq(par1, par3, par5);

			if ((par7 < 0.0D || var14 < par7 * par7)
					&& (var9 == -1.0D || var14 < var9)) {
				var9 = var14;
				var11 = var13;
			}
		}

		return var11;
	}

	public int blockTimer = 30;

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.health < 1) {
			heldItem = null;
		}
		if (!isVending) {
			moveSpeed = (float) (this.entityToAttack != null ? 1.0 : 0.6);
		}
		if (block) {
			blockTimer--;
			if (blockTimer == 0) {
				block = false;
				blockTimer = 30;
			}
		}
	}

	@Override
	public boolean getCanSpawnHere() {
			int x = MathHelper.floor_double(posX);
			int y = MathHelper.floor_double(boundingBox.minY);
			int z = MathHelper.floor_double(posZ);
			int spawnBlock = worldObj.getBlockId(x, y - 1, z);
			worldObj.checkIfAABBIsClear(boundingBox);
			if (worldObj.isDaytime() && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox)){
					if(y > 60){
						return true;
					}
					
			}
		return false;
	}

	@Override
	public boolean isSprinting() {
		return this.entityToAttack == null ? false : (this.entityToAttack
				.getDistanceToEntity(this) > 3D);
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		// TODO Auto-generated method stub
		this.swingItem();
		this.worldObj.playSoundAtEntity(this,
				"okushama.humansplus.sword_attack", 0.5F, 1.0F);
		if (rand.nextInt(2) == 0) {
			this.worldObj.playSoundAtEntity(this,
					"okushama.humansplus.sword_hit", 0.5F, 1.0F);

		}
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	public boolean isBlocking() {
		return block;
	}

	public boolean block = false;

	@Override
	protected void damageEntity(DamageSource par1DamageSource, int par2) {
		if (par1DamageSource.getEntity() != null) {
			if (rand.nextInt(4) == 0
					&& this.getHeldItem().getItem() instanceof ItemSword) {
				block = true;
				par2 = par2 / 2;
			}
		}
		super.damageEntity(par1DamageSource, par2);
		block = false;
	}

	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		// TODO Auto-generated method stub
		super.attackEntity(par1Entity, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getHeldItem() {
		return heldItem;
	}

	@Override
	public boolean canDespawn() {
		return !this.isVending;
	}

	public ItemStack dropItem;

	@Override
	public int getDropItemId() {
		int drop = 0;
		drop = getHeldItem().getItem().shiftedIndex;
		return dropItem.getItem().shiftedIndex;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("mob_tex", texture);
		if(heldItem != null)
		nbt.setInteger("mob_held", heldItem.getItem().shiftedIndex);
		if(dropItem != null)
		nbt.setInteger("mob_drop", dropItem.getItem().shiftedIndex);
		super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		texture = nbt.getString("mob_tex");
		try{
		heldItem = new ItemStack(Item.itemsList[nbt.getInteger("mob_held")]);
		dropItem = new ItemStack(Item.itemsList[nbt.getInteger("mob_drop")]);
		}catch(Exception e){
			
		}
		super.readFromNBT(nbt);
	}

	private EnumCreatureAttribute attribute = EnumHelper
			.addCreatureAttribute("HUMAN");

	@Override
	public String getHurtSound() {
		return "okushama.humansplus.mantakedamage";
	}

	@Override
	public String getDeathSound() {
		return "okushama.humansplus.manchoke";
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return attribute;
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		if (texture.isEmpty()) {
			texture = RegistryTextures.getTexture(this);
		}
		if (heldItem == null) {
			heldItem = new ItemStack(potentialHeldItems.get(rand
					.nextInt(potentialHeldItems.size())));
		}
		if (dropItem == null) {
			dropItem = heldItem;
		}
		data.writeUTF(texture);
		data.writeInt(heldItem.getItem().shiftedIndex);
		data.writeInt(dropItem.getItem().shiftedIndex);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var3 = this.getDropItemId();

		if (var3 > 0) {
			int var4 = this.rand.nextInt(3);

			if (par2 > 0) {
				var4 += this.rand.nextInt(par2 + 1);
			}

			// for (int var5 = 0; var5 < var4; ++var5)
			// {
			this.dropItem(var3, 1);
			// }
		}
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		String tex = data.readUTF();
		texture = tex;
		heldItem = new ItemStack(Item.itemsList[data.readInt()]);
		dropItem = new ItemStack(Item.itemsList[data.readInt()]);
	}

}
