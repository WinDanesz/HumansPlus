package okushama.humansplus;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHuman extends RenderLiving<Human>
{
    private static final ResourceLocation humanTextures = new ResourceLocation("humansplus:textures/entity/human.png");

    public RenderHuman(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelHuman(0.0F), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Human entity)
    {
        return humanTextures;
    }
}
