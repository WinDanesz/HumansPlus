package okushama.humansplus;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import static net.minecraftforge.client.IItemRenderer.ItemRenderType.*;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.*;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHuman extends RenderLiving
{
    private ModelHuman modelBipedMain;
    private ModelHuman modelArmorChestplate;
    private ModelHuman modelArmor;
    public static String[] armorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};
    public static float NAME_TAG_RANGE = 64.0f;
    public static float NAME_TAG_RANGE_SNEAK = 32.0f;

    public RenderHuman()
    {
        super(new ModelHuman(0.0F), 0.5F);
        this.modelBipedMain = (ModelHuman)this.mainModel;
        this.modelArmorChestplate = new ModelHuman(1.0F);
        this.modelArmor = new ModelHuman(0.5F);
    }

    /**
     * Set the specified armor model as the player model. Args: player, armorSlot, partialTick
     */
   /* protected int setArmorModel(Human par1EntityPlayer, int par2, float par3)
    {
        ItemStack var4 = par1EntityPlayer.inventory.armorItemInSlot(3 - par2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture(ForgeHooksClient.getArmorTexture(var4, "/armor/" + armorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png"));
                ModelBiped var7 = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
                var7.bipedHead.showModel = par2 == 0;
                var7.bipedHeadwear.showModel = par2 == 0;
                var7.bipedBody.showModel = par2 == 1 || par2 == 2;
                var7.bipedRightArm.showModel = par2 == 1;
                var7.bipedLeftArm.showModel = par2 == 1;
                var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
                var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
                this.setRenderPassModel(var7);

                if (var7 != null)
                {
                    var7.onGround = this.mainModel.onGround;
                }

                if (var7 != null)
                {
                    var7.isRiding = this.mainModel.isRiding;
                }

                if (var7 != null)
                {
                    var7.isChild = this.mainModel.isChild;
                }

                float var8 = 1.0F;

                if (var6.func_82812_d() == EnumArmorMaterial.CLOTH)
                {
                    int var9 = var6.func_82814_b(var4);
                    float var10 = (float)(var9 >> 16 & 255) / 255.0F;
                    float var11 = (float)(var9 >> 8 & 255) / 255.0F;
                    float var12 = (float)(var9 & 255) / 255.0F;
                    GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);

                    if (var4.isItemEnchanted())
                    {
                        return 31;
                    }

                    return 16;
                }

                GL11.glColor3f(var8, var8, var8);

                if (var4.isItemEnchanted())
                {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }*/

   /* protected void func_82439_b(Human par1EntityPlayer, int par2, float par3)
    {
        ItemStack var4 = par1EntityPlayer.inventory.armorItemInSlot(3 - par2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture(ForgeHooksClient.getArmorTexture(var4, "/armor/" + armorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + "_b.png"));
                float var7 = 1.0F;
                GL11.glColor3f(var7, var7, var7);
            }
        }
    }*/

    public float delta = 0F;
    public void renderPlayer(Human par1EntityPlayer, double par2, double par4, double par6, float par8, float par9)
    {
    	delta = par9;
        float var10 = 1.0F;
        GL11.glColor3f(var10, var10, var10);
        ItemStack var11 = par1EntityPlayer.heldItem;
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = var11 != null ? 1 : 0;

        if (var11 != null && par1EntityPlayer.heldItem != null)
        {
            EnumAction var12 = var11.getItemUseAction();

            if (var12 == EnumAction.block)
            {
                this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;
            }
            else if (var12 == EnumAction.bow)
            {
                this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
            }
        }

        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1EntityPlayer.isSneaking();
        double var14 = par4 - (double)par1EntityPlayer.yOffset;

        if (par1EntityPlayer.isSneaking())
        {
            var14 -= 0.125D;
        }

        super.doRenderLiving(par1EntityPlayer, par2, var14, par6, par8, par9);
        this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
    }

    protected void func_82440_a(Human par1EntityPlayer, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if (!par1EntityPlayer.func_82150_aj())
        {
            super.renderModel(par1EntityPlayer, par2, par3, par4, par5, par6, par7);
        }
    }

    /**
     * Used to render a player's name above their head
     */
    
    public long lifeTick = 0;
    public long lastWorldTick = 0;
    protected void renderName(Human human, double par2, double par4, double par6)
    {
    	if(lastWorldTick != human.worldObj.getWorldTime()){
    		lastWorldTick = human.worldObj.getWorldTime();
    		lifeTick++;
    	}
        if (Minecraft.isGuiEnabled() && !human.func_82150_aj())
        {
            float var8 = 1.6F;
            float var9 = 0.016666668F * var8;
            double var10 = human.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float var12 = human.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
            if(var10 > 10D){
            	human.isInRangeToVend = false;
            }else{
            	human.isInRangeToVend = true;
            }
            if (var10 < (double)(var12 * var12))
            {
            	
            	String name = "Human";
            	if(human.getDataWatcher().getWatchableObjectString(10).equals("null")){
            		human.setName(human.getEntityName());
            	}
            	name = human.getDataWatcher().getWatchableObjectString(10);
                
               
                boolean isVending = human.getDataWatcher().getWatchableObjectByte(11) == 1;
                if(isVending){
                	this.renderLivingLabel(human, name, par2, par4, par6, 64);
                GL11.glPushMatrix();
                Minecraft mc = Minecraft.getMinecraft();
                float yOffset = MathHelper.sin(((float)lifeTick + delta) / 10.0F) * 0.1F + 0.1F;
            	
				GL11.glTranslatef((float)par2 , (float)par4 + yOffset + 4.4F, (float)par6);
				float var15 = 1.6F;
		        float var14 = 0.016666668F * var15;
		        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		        GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		        GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		        GL11.glScalef(-var14, -var14, 0F);
		        GL11.glDisable(GL11.GL_LIGHTING);
		      //  GL11.glDepthMask(false);
		        GL11.glDisable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/okushama/humansplus/test.png"));	
		        mc.ingameGUI.drawTexturedModalRect(-15, 0, 0, 110, 22, 71);
				
		     //   GL11.glDepthMask(true);
		        GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
                }
            }
        }
    }

    /**
     * Method for adding special render rules
     */
    protected void renderSpecials(Human par1EntityPlayer, float par2)
    {
        float var3 = 1.0F;
        GL11.glColor3f(var3, var3, var3);
        super.renderEquippedItems(par1EntityPlayer, par2);
        ItemStack var4 = null;

        if (var4 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625F);
            float var5;

            if (var4 != null && var4.getItem() instanceof ItemBlock)
            {
                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(var4, EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, var4, BLOCK_3D));
        
                if (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType()))
                {
                    var5 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(var5, -var5, -var5);
                }

                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var4, 0);
            }
            else if (var4.getItem().shiftedIndex == Item.field_82799_bQ.shiftedIndex)
            {
                var5 = 1.0625F;
                GL11.glScalef(var5, -var5, -var5);
                String var6 = "";

                if (var4.hasTagCompound() && var4.getTagCompound().hasKey("SkullOwner"))
                {
                    var6 = var4.getTagCompound().getString("SkullOwner");
                }

                TileEntitySkullRenderer.field_82397_a.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var4.getItemDamage(), var6);
            }

            GL11.glPopMatrix();
        }

        float var7;
        float var11;
        ItemStack var21 = par1EntityPlayer.heldItem;

        if (var21 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            EnumAction var23 = null;

            if (par1EntityPlayer.heldItem != null)
            {
                var23 = var21.getItemUseAction();
            }

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(var21, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, var21, BLOCK_3D));
            
            if (var21.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[var21.itemID].getRenderType())))
            {
                var7 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                var7 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
            }
            else if (var21.itemID == Item.bow.shiftedIndex)
            {
                var7 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[var21.itemID].isFull3D())
            {
                var7 = 0.625F;

                if (Item.itemsList[var21.itemID].shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

               /* if (par1EntityPlayer.heldItem != null && var23 == EnumAction.block)
                {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }*/

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                var7 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(var7, var7, var7);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            if (var21.getItem().requiresMultipleRenderPasses())
            {
                for (int var27 = 0; var27 < var21.getItem().getRenderPasses(var21.getItemDamage()); ++var27)
                {
                    int var26 = var21.getItem().func_82790_a(var21, var27);
                    float var28 = (float)(var26 >> 16 & 255) / 255.0F;
                    float var10 = (float)(var26 >> 8 & 255) / 255.0F;
                    var11 = (float)(var26 & 255) / 255.0F;
                    GL11.glColor4f(var28, var10, var11, 1.0F);
                    this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var21, var27);
                }
            }
            else
            {
                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var21, 0);
            }

            GL11.glPopMatrix();
        }
    }
    
    protected void renderLivingLabel(EntityLiving par1EntityLiving, String par2Str, double par3, double par5, double par7, int par9)
    {
        double var10 = par1EntityLiving.getDistanceSqToEntity(this.renderManager.livingPlayer);

        if (var10 <= (double)(par9 * par9))
        {
            FontRenderer var12 = this.getFontRendererFromRenderManager();
            float var13 = 1.6F;
            float var14 = 0.016666668F * var13;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + 2.3F, (float)par7);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-var14, -var14, var14);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator var15 = Tessellator.instance;
            byte var16 = 0; 
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            var15.startDrawingQuads();
            int var17 = var12.getStringWidth(par2Str) / 2;
            var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            var15.addVertex((double)(-var17 - 1), (double)(-1 + var16), 0.0D);
            var15.addVertex((double)(-var17 - 1), (double)(8 + var16), 0.0D);
            var15.addVertex((double)(var17 + 1), (double)(8 + var16), 0.0D);
            var15.addVertex((double)(var17 + 1), (double)(-1 + var16), 0.0D);
            var15.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2, var16, 553648127);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2, var16, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    protected void renderPlayerScale(Human par1EntityPlayer, float par2)
    {
        float var3 = 0.9375F;
        GL11.glScalef(var3, var3, var3);
    }

    public void func_82441_a(Human par1EntityPlayer)
    {
        float var2 = 1.0F;
        GL11.glColor3f(var2, var2, var2);
        this.modelBipedMain.onGround = 0.0F;
        this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
        this.modelBipedMain.bipedRightArm.render(0.0625F);
    }


    /**
     * Passes the specialRender and renders it
     */
    protected void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
        this.renderName((Human)par1EntityLiving, par2, par4, par6);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
    {
        this.renderPlayerScale((Human)par1EntityLiving, par2);
    }

    protected void func_82408_c(EntityLiving par1EntityLiving, int par2, float par3)
    {
       // this.func_82439_b((Human)par1EntityLiving, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
   /* protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.setArmorModel((Human)par1EntityLiving, par2, par3);
    }*/

    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        this.renderSpecials((Human)par1EntityLiving, par2);
    }

    protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
    	super.rotateCorpse(par1EntityLiving, par2, par3, par4);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
       super.renderLivingAt(par1EntityLiving, par2, par4, par6);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.func_82440_a((Human)par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderPlayer((Human)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderPlayer((Human)par1Entity, par2, par4, par6, par8, par9);
    }
}
