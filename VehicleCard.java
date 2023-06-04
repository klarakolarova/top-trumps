package a12128598;

import java.util.LinkedHashMap; 

import java.util.Map;
import java.util.Objects;

public class VehicleCard implements Comparable<VehicleCard> {

	//NESTED ENUM - CATEGORY
	public enum Category {
		ECONOMY_MPG ("Miles/Gallon"),
		CYLINDERS_CNT ("Zylinder"),
		DISPLACEMENT_CCM ("Hubraum[cc]"),
		POWER_HP ("Leistung[hp]"),
		WEIGHT_LBS ("Gewicht[lbs]") {
			@Override
			public boolean isInverted() { 
				return true;
			}
		},
		ACCELERATION ("Beschleunigung") {
			@Override
			public boolean isInverted() { 
				return true;
			}
		},
		YEAR ("Baujahr[19xx]");

		//category - attribute
		private final String categoryName;
		
		//category - ctor
		private Category(final String categoryName) {
			if (categoryName == null || categoryName.isEmpty()) {
				throw new IllegalArgumentException("Name not set or empty - Enum Vehicle Card Category");
			}
			this.categoryName = categoryName;
		}

		//category - methods
		public boolean isInverted() { 
			return false;
		}

		//category (added exception)
		public int bonus(final Double value) { 
			if (value == null) throw new IllegalArgumentException("Argument was null - VehicleCards.Category.bonus");
			if (this.isInverted()) return (int)(-value);
			else return (int)(+value);
		}

		@Override
		public String toString() {
			return this.categoryName;
		} 
	}
	//END OF NESTED ENUM CATEGORY
	
	//VEHICLE_CARD - ATTRIBUTES
	private String name;
	private Map<Category, Double> categories;

	//VEHICLE_CARD - CTOR
	public VehicleCard(final String name, final Map<Category, Double> categories) {
		if (name == null || name.isEmpty()) throw new IllegalArgumentException ("Name is null or empty - Vehicle card ctor");
		if (categories == null) throw new IllegalArgumentException ("categories is null - Vehicle card ctor");
		if (categories.size() != Category.values().length) throw new IllegalArgumentException("Map has invalid size - Vehicle card ctor"); //added exception
		
		for (Category c: Category.values()) {
			if (! categories.containsKey(c)) throw new IllegalArgumentException("Category missing in categories - Vehicle card ctor");
		}

		for (Map.Entry<Category, Double> e: categories.entrySet()) {
			if (e.getValue() == null || e.getValue() <0 ) throw new IllegalArgumentException ("categories value null or negative - Vehicle card ctor");
		}

		this.name = name;
		this.categories = new LinkedHashMap<Category, Double>(categories);
	}

	
	//VEHICLE_CARD - METHODS : getters for _immutable_ class, no setters (!) 
	
	public String getName() {
		return this.name;
	}
	
	public Map<Category, Double> getCategories() {
		return new LinkedHashMap<Category, Double>(this.categories);
	}

	//factory method for categories Map
	public static Map<Category, Double> newMap(double economy, double cylinders, double displacement, double power,
			double weight, double acceleration, double year) {
		
		Map<Category, Double> myMap = new LinkedHashMap<Category, Double>();

		myMap.put(Category.ECONOMY_MPG, economy);
		myMap.put(Category.CYLINDERS_CNT, cylinders);
		myMap.put(Category.DISPLACEMENT_CCM, displacement);
		myMap.put(Category.POWER_HP, power);
		myMap.put(Category.WEIGHT_LBS, weight);
		myMap.put(Category.ACCELERATION, acceleration);
		myMap.put(Category.YEAR, year);
		
		return myMap;
	}
	
	
	@Override  // compareTo (added exception)
	public int compareTo(final VehicleCard other) {
		if (other == null) throw new IllegalArgumentException ("Argument was null - VehicleCard.compareTo");
		return this.totalBonus() - other.totalBonus();
	}

	
	public int totalBonus() {
		int sum = 0;
		for (Map.Entry<Category, Double> e: categories.entrySet()) {
			sum += e.getKey().bonus(e.getValue());
		}
		return sum;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(name, totalBonus());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof VehicleCard)) return false; //covers null
		
		VehicleCard objVC = (VehicleCard)obj;
		
		return (this.name.equals(objVC.name) && this.totalBonus() == objVC.totalBonus());
	}

	@Override
	public String toString() {
			
		StringBuilder res = new StringBuilder("- " + this.name + "(" + this.totalBonus() + ") -> {");
		
		boolean first = true;
		for (Map.Entry<Category, Double> e: categories.entrySet()) {
			if (first) first = false;
			else res.append(", ");
			
			res.append(e.getKey().toString() + "=" + e.getValue());
		}
		
		res.append("}");

		return res.toString();
	}

}
