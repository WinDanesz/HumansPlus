package okushama.humansplus;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.LanguageRegistry;


import net.minecraft.src.*;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.EnumHelper;

public class RegistrySword {
		
	public static ArrayList<Sword> swords = new ArrayList<Sword>();
	public static EnumRarity legendary;
	public static int LEGENDARY = 0, ANCIENT = 1, COMMON = 2;
	public static EnumToolMaterial[] material = {
		EnumHelper.addToolMaterial("LEGENDARY", 3, 2000, 4F, 6, 10),
		EnumHelper.addToolMaterial("ANCIENT", 2, 225, 1.1F, 8, 40),
		EnumHelper.addToolMaterial("COMMON", 2, 250, 6F, 3, 18)		
	};
	
	public RegistrySword(){
		MinecraftForgeClient.preloadTexture("/okushama/humansplus/swords.png");
		legendary = EnumHelperClient.addRarity("legendary", 5, "Legendary");	
		// Sword Registration!
		add(846, "Hidden Blade", material[COMMON]);
		add(847, "Journeyman's Blade", material[COMMON]);
		add(848, "Ornate Blade", material[ANCIENT]);
		add(849, "Broken Blade", material[COMMON]);
		add(850, "Katana", material[COMMON]);	
		add(851, "Devouros", material[ANCIENT]);
		add(852, "Gladios", material[ANCIENT]);
		add(853, "Lich Bane", material[ANCIENT]);
		add(854, "Bastard Sword", material[COMMON]);
		add(855, "Malaikah", material[ANCIENT]);
		add(856, "Aquatos", material[LEGENDARY]);
		add(857, "Infernos", material[LEGENDARY]);
		add(858, "Epachos", material[LEGENDARY]);
		add(859, "Solanis", material[LEGENDARY]);
		add(860, "Lunos", material[LEGENDARY]);
		add(861, "Mortis", material[LEGENDARY]);
	}
	
	public void add(int id, String name, EnumToolMaterial mat){
		swords.add(new Sword(id, name,mat));	
	}

	public static class Sword{	
		public String swordName;
		public EnumToolMaterial material;
		public okushama.humansplus.Sword theSword;
		int id;
		public Sword(int i, String name, EnumToolMaterial equiv){
			id = i;
			swordName = name;
			material = equiv;
			theSword = (okushama.humansplus.Sword) (new okushama.humansplus.Sword(id, material)
			.setIconCoord(id-846,0)
			.setItemName(swordName)
			.setCreativeTab(CreativeTabs.tabCombat));
			LanguageRegistry.addName(theSword, swordName);
		}
	}

}
