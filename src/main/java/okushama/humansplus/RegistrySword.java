package okushama.humansplus;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RegistrySword {
		
	public static ArrayList<SwordEntry> swords = new ArrayList<SwordEntry>();
	public static int LEGENDARY = 0, ANCIENT = 1, COMMON = 2;
	public static Item.ToolMaterial[] material = {
		EnumHelper.addToolMaterial("LEGENDARY", 3, 2000, 4F, 6, 10),
		EnumHelper.addToolMaterial("ANCIENT", 2, 225, 1.1F, 8, 40),
		EnumHelper.addToolMaterial("COMMON", 2, 250, 6F, 3, 18)		
	};
	
	public RegistrySword(){
		// Sword Registration!
		add("hidden_blade", "Hidden Blade", material[COMMON]);
		add("journeymans_blade", "Journeyman's Blade", material[COMMON]);
		add("ornate_blade", "Ornate Blade", material[ANCIENT]);
		add("broken_blade", "Broken Blade", material[COMMON]);
		add("katana", "Katana", material[COMMON]);	
		add("devouros", "Devouros", material[ANCIENT]);
		add("gladios", "Gladios", material[ANCIENT]);
		add("lich_bane", "Lich Bane", material[ANCIENT]);
		add("bastard_sword", "Bastard Sword", material[COMMON]);
		add("malaikah", "Malaikah", material[ANCIENT]);
		add("aquatos", "Aquatos", material[LEGENDARY]);
		add("infernos", "Infernos", material[LEGENDARY]);
		add("epachos", "Epachos", material[LEGENDARY]);
		add("solanis", "Solanis", material[LEGENDARY]);
		add("lunos", "Lunos", material[LEGENDARY]);
		add("mortis", "Mortis", material[LEGENDARY]);
	}
	
	public void add(String registryName, String displayName, Item.ToolMaterial mat){
		Sword sword = new Sword(mat);
		sword.setRegistryName(new ResourceLocation(ModHumansPlus.MODID, registryName));
		sword.setUnlocalizedName(registryName);
		ForgeRegistries.ITEMS.register(sword);
		swords.add(new SwordEntry(displayName, sword, mat));	
	}

	public static class SwordEntry {	
		public String swordName;
		public Item.ToolMaterial material;
		public okushama.humansplus.Sword theSword;
		
		public SwordEntry(String name, okushama.humansplus.Sword sword, Item.ToolMaterial mat){
			swordName = name;
			theSword = sword;
			material = mat;
		}
	}

}
