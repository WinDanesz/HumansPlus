package okushama.humansplus.quest;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import okushama.humansplus.Panel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class PanelQuestImage extends Panel{

	public String img;
	public PanelQuestImage(int xx, int yy, int ww, int hh, String tt, int imageOffx, int imageOffy) {
		super(xx, yy, ww, hh, "/okushama/humansplus/test2.png");
		img = tt;
		imgOffsetX = imageOffx;
		imgOffsetY = imageOffy;
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		
	}
	
	public int i = 38;
	public int imgOffsetX = 0;
	public int imgOffsetY = 0;
	
	public void drawImageInside(){
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glPushMatrix();
		mc.renderEngine.bindTexture(new ResourceLocation(img));	
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTranslatef((float)x+1, (float)y+1, 0F);
		mc.ingameGUI.drawTexturedModalRect(0, 0, i*imgOffsetX, i*imgOffsetY, 38, 38);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		Random rand = new Random();
	}
	
	@Override
	public void onUpdate(){

	}

}
