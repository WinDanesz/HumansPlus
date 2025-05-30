package okushama.humansplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Sword extends ItemSword
{
	private int damage;
	private ToolMaterial material;
	
    public Sword(ToolMaterial mat)
    {
        super(mat);
        material = mat;
        damage = 4 + (int)mat.getAttackDamage();
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, net.minecraft.block.state.IBlockState state)
    {
        Block block = state.getBlock();
        return block == net.minecraft.init.Blocks.WEB ? 15.0F : 1.5F;
    }
    
    public Random rand = new Random();

    @Override
    public boolean hitEntity(ItemStack thisStack, EntityLivingBase attacked, EntityLivingBase attacker)
    {
    	String name = thisStack.getItem().getRegistryName().getResourcePath();
    	if(this.material == RegistrySword.material[RegistrySword.LEGENDARY]){		
    		if(name.equals("mortis")){
    			if(attacked.getCreatureAttribute() != net.minecraft.entity.EnumCreatureAttribute.UNDEAD){
    				attacked.attackEntityFrom(net.minecraft.util.DamageSource.causeMobDamage(attacker), 100);
    			}
    		}
    	}
    	if(name.equals("malaikah")){
    		attacker.world.createExplosion(attacker, attacked.posX, attacked.posY, attacked.posZ, 0F, false);
    	}
    	if(name.equals("infernos")){
    		if(!attacked.isWet()){
    			attacked.setFire(20);
    		}
    	}
    	if(name.equals("solanis")){
    		if(attacked instanceof net.minecraft.entity.monster.EntityEnderman){
    			if(attacked.getRNG().nextInt(5) == 0){
    				attacker.world.setWorldTime(0);
    				// Chat message handling would need to be done differently in 1.12
    			}
    		}
    	}
        thisStack.damageItem(1, attacked);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, net.minecraft.block.state.IBlockState state, 
    		net.minecraft.util.math.BlockPos pos, EntityLivingBase entityLiving)
    {
        if (state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }

    // maximum duration
    public int getMaxItemUseDuration(ItemStack theStack)
    {
        return 72000;
    }
    
    // Note: getTextureFile() is no longer used in 1.12.2 - textures are handled through resource packs
    /*
    @Override
    public String getTextureFile()
    {
            return "/okushama/humansplus/swords.png";
    }
    */

    // on Right Click set in use
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack thisStack = playerIn.getHeldItem(handIn);
        if(worldIn.isRemote){
            return new ActionResult<>(EnumActionResult.PASS, thisStack);
        }
        HumanHunter hunter = new HumanHunter(worldIn);
        hunter.setPositionAndRotation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
        worldIn.spawnEntity(hunter);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, thisStack);
    }
    
    // set state to blocking when in use
    @Override
    public EnumAction getItemUseAction(ItemStack thisStack)
    {
        return EnumAction.BLOCK;
    }

    // Blocks this sword can harvest - updated for 1.12.2
    public boolean canHarvestBlock(net.minecraft.block.state.IBlockState blockState)
    {
        return blockState.getBlock() == net.minecraft.init.Blocks.WEB;
    }


    // Enchantability Factor
    @Override
    public int getItemEnchantability()
    {
        return material.getEnchantability();
    }

    // Get Material name - replaced with proper method name in 1.12.2
    public String getToolMaterialName()
    {
        return material.toString();
    }
    
    @SideOnly(Side.CLIENT)

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
		return par1ItemStack.isItemEnchanted() ? EnumRarity.RARE : 
		       this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) ? EnumRarity.EPIC : 
		       this.material.equals(RegistrySword.material[RegistrySword.ANCIENT]) ? EnumRarity.RARE : 
		       EnumRarity.COMMON;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) || par1ItemStack.isItemEnchanted();
    }
    
   
    
    
}
