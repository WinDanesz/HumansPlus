package okushama.humansplus;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class Sword extends ItemSword
{

	private int damage;
	private EnumToolMaterial material;
	
	
    public Sword(int id, EnumToolMaterial mat)
    {
        super(id, mat);
        material = mat;
        damage = 4 + mat.getDamageVsEntity();
    }

    // get speed for mining certain blocks
    @Override
    public float getStrVsBlock(ItemStack thisStack, Block theBlock)
    {
        return theBlock.blockID == Block.web.blockID ? 15.0F : 1.5F;
    }
    
    public Random rand = new Random();

    // entity hit by sword
    @Override
    public boolean hitEntity(ItemStack thisStack, EntityLiving attacked, EntityLiving attacker)
    {
    	String name = itemsList[shiftedIndex].getItemName();
    	if(this.material == RegistrySword.material[RegistrySword.LEGENDARY]){		
    		if(name.equals("item.Mortis")){
    			if(attacked.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD){
    				attacked.attackEntityFrom(DamageSource.causeMobDamage(attacker), 100);
    			}
    		}
    	}
    	if(name.equals("item.Malaikah")){
    		attacker.worldObj.createExplosion(attacker, attacked.posX, attacked.posY, attacked.posZ, 0F, false);
    	}
    	if(name.equals("item.Infernos")){
    		if(!attacked.isWet()){
    			attacked.setFire(20);
    		}
    	}
    	if(name.equals("item.Solanis")){
    		if(attacked instanceof EntityEnderman){
    			if(attacked.getRNG().nextInt(5) == 0){
    				if(Minecraft.getMinecraft().getIntegratedServer() != null){
    				Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(attacker.worldObj.getWorldInfo().getDimension()).getWorldInfo().setWorldTime(0);
    				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("Solanis resonated upon hitting the");
    				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("Creature of Darkness and brought forth");
    				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("the Sun, ending the acursed night.");
    				}
    				
    			}
    		}
    	}
        thisStack.damageItem(1, attacked);
        return true;
    }

    // Block broken with sword
    @Override
    public boolean onBlockDestroyed(ItemStack thisStack, World thisWorld, int blockID, int blockX,
    		int blockY, int blockZ, EntityLiving theUser)
    {
    	// If ANY block is destroyed with the sword, inflict double damage on the sword
        if ((double)Block.blocksList[blockID].getBlockHardness(thisWorld, blockX, blockY, blockZ) != 0.0D)
        {
            thisStack.damageItem(2, theUser);
        }

        return true;
    }

    // get the amount of damage to inflict on the entity
    @Override
    public int getDamageVsEntity(Entity attacked)
    {
        return this.damage;
    }

    // maximum duration
    public int getMaxItemUseDuration(ItemStack theStack)
    {
        return 72000;
    }
    
    @Override
    public String getTextureFile()
    {
            return "/okushama/humansplus/swords.png";
    }

    // on Right Click set in use
    @Override
    public ItemStack onItemRightClick(ItemStack thisStack, World thisWorld, EntityPlayer thePlayer)
    {
    	if(thisWorld.isRemote){
    		return thisStack;
    	}
    	HumanHunter hunter = new HumanHunter(thisWorld);
    	hunter.setPositionAndRotation(thePlayer.posX, thePlayer.posY, thePlayer.posZ, thePlayer.rotationYaw, thePlayer.rotationPitch);
    	thisWorld.spawnEntityInWorld(hunter);
        thePlayer.setItemInUse(thisStack, this.getMaxItemUseDuration(thisStack));
        return thisStack;
    }
    
    // set state to blocking when in use
    @Override
    public EnumAction getItemUseAction(ItemStack thisStack)
    {
        return EnumAction.block;
    }

    // Blocks this sword can harvest
    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID;
    }


    // Enchantability Factor
    @Override
    public int getItemEnchantability()
    {
        return material.getEnchantability();
    }

    // Get Material name
    @Override
    public String func_77825_f()
    {
        return material.toString();
    }
    
    @SideOnly(Side.CLIENT)

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
		return par1ItemStack.isItemEnchanted() ? EnumRarity.rare : this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) ? RegistrySword.legendary : ( this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) ? EnumRarity.rare : EnumRarity.common);
    	
    	
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) || par1ItemStack.isItemEnchanted();
    }
    
   
    
    
}
