package okushama.humansplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
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
    public net.minecraft.util.EnumRarity getRarity(ItemStack par1ItemStack)
    {
		return par1ItemStack.isItemEnchanted() ? net.minecraft.util.EnumRarity.RARE : 
		       this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) ? net.minecraft.util.EnumRarity.EPIC : 
		       this.material.equals(RegistrySword.material[RegistrySword.ANCIENT]) ? net.minecraft.util.EnumRarity.RARE : 
		       net.minecraft.util.EnumRarity.COMMON;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return this.material.equals(RegistrySword.material[RegistrySword.LEGENDARY]) || par1ItemStack.isItemEnchanted();
    }
    
   
    
    
}
