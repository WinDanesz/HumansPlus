package okushama.humansplus;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHuman extends ModelBiped
{
    public ModelHuman(float scale)
    {
        super(scale);
    }

    public ModelHuman(float scale, float yOffset, int textureWidth, int textureHeight)
    {
        super(scale, yOffset, textureWidth, textureHeight);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        
        if (entityIn instanceof Human) {
            Human human = (Human) entityIn;
            
            // Set specific poses for different human types or states
            if (human.isActiveItemStackBlocking()) {
                // Blocking pose
                this.bipedRightArm.rotateAngleX = -0.5F;
                this.bipedRightArm.rotateAngleY = 0.3F;
                this.bipedLeftArm.rotateAngleX = -0.5F;
                this.bipedLeftArm.rotateAngleY = -0.3F;
            }
        }
    }
}
