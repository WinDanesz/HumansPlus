package okushama.humansplus;

import java.util.ArrayList;

import okushama.humansplus.quest.HandlerQuests;
import okushama.humansplus.quest.Quest;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Human extends EntityMob implements IMob, IEntityAdditionalSpawnData {
	
	public int alignment = 0;
	public static int GOOD = 1, EVIL = -1;
	
	private static final DataParameter<String> NAME = EntityDataManager.<String>createKey(Human.class, DataSerializers.STRING);
	private static final DataParameter<Boolean> IS_VENDING = EntityDataManager.<Boolean>createKey(Human.class, DataSerializers.BOOLEAN);

	public Human(World world) {
		super(world);
		this.setHealth(20.0F);
		setPotentialHeldItems();
		heldItem = null;
		dropItem = null;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIBreakDoor(this));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		// EntityAIMoveTowardsVillage doesn't exist in 1.12.2 - replacing with AI task
		this.tasks.addTask(4, new EntityAIWander(this, 0.6D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
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
	public String texture = "";
	public boolean isVending = false;
	public boolean isInRangeToVend = false;
	public float moveSpeed = 1F;
	public ArrayList<Class> targetTypes = new ArrayList<Class>();
	public int blockTimer = 30;
	public boolean block = false;
	public ItemStack dropItem;

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(NAME, this.name);
		this.dataManager.register(IS_VENDING, Boolean.valueOf(this.isVending));
	}

	public void setVendingQuest(boolean b) {
		isVending = b;
		this.dataManager.set(IS_VENDING, Boolean.valueOf(this.isVending));
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (HandlerQuests.currentQuest != null) {
			if (!HandlerQuests.currentQuest.hasStarted && this.isVending) {
				HandlerQuests.currentQuest.startQuest();
				return true;
			}
			if (HandlerQuests.currentQuest.type == Quest.COLLECTION
					&& this.isVending) {
				HandlerQuests.currentQuest.collection.onInteractWithVendor(player);
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
		this.dataManager.set(NAME, String.valueOf(this.name));
	}
	
	public String getName() {
        return this.name;
    }

	public void setPotentialHeldItems() {
		if(ModHumansPlus.swords != null && ModHumansPlus.swords.swords.size() > 4) {
			potentialHeldItems.add(ModHumansPlus.swords.swords.get(4).theSword);
		}
	}

	public void setTargetTypes(Class<? extends EntityLiving>[] mobs) {
		// Implementation for setting target types
	}

	public EntityLiving getClosestEntity(ArrayList<Class> type, double par1,
			double par3, double par5, double par7) {
		double var9 = -1.0D;
		EntityLiving var11 = null;

		for (net.minecraft.entity.Entity entity : world.getLoadedEntityList()) {
			if (!(entity instanceof EntityLiving)) {
				continue;
			}
			EntityLiving var13 = (EntityLiving) entity;
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

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getHealth() < 1) {
			heldItem = null;
		}
		if (!isVending) {
			// moveSpeed adjustment logic here
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
		int x = (int)Math.floor(this.posX);
		int y = (int)Math.floor(this.getEntityBoundingBox().minY);
		int z = (int)Math.floor(this.posZ);
		
		if (world.isDaytime() && y > 60){
			return !world.containsAnyLiquid(this.getEntityBoundingBox()) && 
				   world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
		}
		return false;
	}

	@Override
	public boolean attackEntityAsMob(net.minecraft.entity.Entity par1Entity) {
		this.swingArm(EnumHand.MAIN_HAND);
		// Play sounds using proper 1.12 methods
		if (rand.nextInt(2) == 0) {
			// Sound playing logic here
		}
		return super.attackEntityAsMob(par1Entity);
	}

	public boolean isActiveItemStackBlocking() {
		return block;
	}

	@Override
	protected void damageEntity(DamageSource damageSource, float damage) {
		if (damageSource.getTrueSource() != null) {
			if (rand.nextInt(4) == 0 && this.getHeldItemMainhand().getItem() instanceof ItemSword) {
				block = true;
				damage = damage / 2;
			}
		}
		super.damageEntity(damageSource, damage);
		block = false;
	}

	@Override
	public ItemStack getHeldItemMainhand() {
		return heldItem != null ? heldItem : ItemStack.EMPTY;
	}

	@Override
	public boolean canDespawn() {
		return !this.isVending;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("mob_tex", texture);
		if(heldItem != null && !heldItem.isEmpty()) {
			NBTTagCompound itemNBT = new NBTTagCompound();
			heldItem.writeToNBT(itemNBT);
			nbt.setTag("mob_held", itemNBT);
		}
		if(dropItem != null && !dropItem.isEmpty()) {
			NBTTagCompound dropNBT = new NBTTagCompound();
			dropItem.writeToNBT(dropNBT);
			nbt.setTag("mob_drop", dropNBT);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		texture = nbt.getString("mob_tex");
		if(nbt.hasKey("mob_held")) {
			heldItem = new ItemStack(nbt.getCompoundTag("mob_held"));
		}
		if(nbt.hasKey("mob_drop")) {
			dropItem = new ItemStack(nbt.getCompoundTag("mob_drop"));
		}
	}

	@Override
	protected net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvent.REGISTRY.getObject(new ResourceLocation("humansplus", "mantakedamage"));
	}

	@Override
	protected net.minecraft.util.SoundEvent getDeathSound() {
		return SoundEvent.REGISTRY.getObject(new ResourceLocation("humansplus", "manchoke"));
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED; // Will need to create custom attribute
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		if (texture.isEmpty()) {
			texture = RegistryTextures.getTexture(this);
		}
		if (heldItem == null || heldItem.isEmpty()) {
			if(!potentialHeldItems.isEmpty()) {
				heldItem = new ItemStack(potentialHeldItems.get(rand.nextInt(potentialHeldItems.size())));
			}
		}
		if (dropItem == null || dropItem.isEmpty()) {
			dropItem = heldItem;
		}
		
		// Write data to buffer
		byte[] textureBytes = texture.getBytes();
		buffer.writeInt(textureBytes.length);
		buffer.writeBytes(textureBytes);
		
		if(heldItem != null && !heldItem.isEmpty()) {
			buffer.writeBoolean(true);
			NBTTagCompound heldNBT = new NBTTagCompound();
			heldItem.writeToNBT(heldNBT);
			// Write NBT to buffer - simplified for now
			buffer.writeInt(Item.getIdFromItem(heldItem.getItem()));
		} else {
			buffer.writeBoolean(false);
		}
		
		if(dropItem != null && !dropItem.isEmpty()) {
			buffer.writeBoolean(true);
			buffer.writeInt(Item.getIdFromItem(dropItem.getItem()));
		} else {
			buffer.writeBoolean(false);
		}
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		// Read texture
		int textureLength = buffer.readInt();
		byte[] textureBytes = new byte[textureLength];
		buffer.readBytes(textureBytes);
		texture = new String(textureBytes);
		
		// Read held item
		if(buffer.readBoolean()) {
			int itemId = buffer.readInt();
			heldItem = new ItemStack(Item.getItemById(itemId));
		}
		
		// Read drop item
		if(buffer.readBoolean()) {
			int itemId = buffer.readInt();
			dropItem = new ItemStack(Item.getItemById(itemId));
		}
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		if (dropItem != null && !dropItem.isEmpty()) {
			this.entityDropItem(dropItem.copy(), 0.0F);
		}
	}
}
