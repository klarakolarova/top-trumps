package a12128598;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FoilVehicleCard extends VehicleCard {

	//FOIL_VEHICLE_CARD ATTRIBUTES (inherited: String name, Map<Category, Double> categories> )
	private Set<Category> specials;

	//FOIL_VEHICLE_CARD CTOR 
	public FoilVehicleCard(final String name, final Map<Category, Double> categories, final Set<Category> specials) {
		super(name, categories);

		if (specials == null || specials.size() == 0 || specials.size() > 3) {
			throw new IllegalArgumentException("specials null or empty or >3 - FoilVC ctor");
		}
		
		for (Category cat : specials) {
			if (cat == null) throw new IllegalArgumentException("specials contains null - FoilVC ctor"); //added exception
		}

		this.specials = new LinkedHashSet<Category>(specials);
	}

	//FOIL_VEHICLE_CARD METHODS (immutable => only getters ! )
	
	public Set<Category> getSpecials() {
		return new LinkedHashSet<Category>(this.specials);
	}

	
	@Override
	public int totalBonus() {
		int base = super.totalBonus();

		for (Category c : specials) {
			base += Math.abs(this.getCategories().get(c));
		}

		return base;
	}

	@Override
	public String toString() {

		StringBuilder res = new StringBuilder("- " + this.getName() + "(" + this.totalBonus() + ") -> {");

		boolean first = true;
		for (Map.Entry<Category, Double> e : this.getCategories().entrySet()) {
			if (first) {
				first = false;
			} else {
				res.append(", ");
			}

			if (specials.contains(e.getKey())) {
				res.append("*" + e.getKey().toString() + "*");
			} else {
				res.append(e.getKey().toString());
			}

			res.append("=" + e.getValue());
		}

		res.append("}");
		return res.toString();
	}

}
