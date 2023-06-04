package a12128598;

import java.util.ArrayDeque; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Player implements Comparable<Player> {

	//PLAYER - ATTRIBUTES
	private String name;
	private Queue<VehicleCard> deck = new ArrayDeque<>();

	//PLAYER - CTOR
	public Player(final String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("name is null or empty -Player ctor");
		this.name = name;
	}

	//PLAYER - METHODS
	
	public String getName() {
		return this.name;
	}

	public int getScore() {

		int score = 0;

		for (VehicleCard vc : deck) {
			score += vc.totalBonus();
		}
		return score;
	}
	
	/* NON-THROWING ALTERNATIVES 
	 
	public void addCards(final Collection<VehicleCard> cards) {
		if (cards != null) {
			for (VehicleCard card: cards) {
				this.addCard(card);
			}
		}
	}

	public void addCard(final VehicleCard card) {
		if (card != null) {
			this.deck.add(card);
		}
	}
	*/
	 
	
	// THROWING ALTERNATIVES 
	public void addCards(final Collection<VehicleCard> cards) {
		if (cards == null) throw new IllegalArgumentException("Null argument - Player.addCards"); //added exception
			for (VehicleCard card: cards) {
				this.addCard(card);
			}
	}
	
	
	public void addCard(final VehicleCard card) {
		if (card == null) throw new IllegalArgumentException("Null argument - Player.addCard"); //added exception
		
		this.deck.add(card);
	}
	//
	
	
	void clearDeck() {
		this.deck.clear();
	}

	List<VehicleCard> getDeck() {
		return new ArrayList<VehicleCard>(this.deck);
	}

	protected VehicleCard peekNextCard() {
		return this.deck.peek();
	}

	public VehicleCard playNextCard() {
		return this.deck.poll();
	}
	
	

	@Override //compareTo (added exception)
	public int compareTo(final Player other) {
		if (other == null) throw new IllegalArgumentException("Null argument - Player.compareTo");
		return this.name.compareToIgnoreCase(other.name);
	}

	@Override
	public int hashCode() {
		return this.name.toLowerCase().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return this.name.equalsIgnoreCase(other.name);
	}

	@Override
	public String toString() {

		StringBuilder res = new StringBuilder(name + "(" + this.getScore() + "):\n");

		boolean first = true;
		for (VehicleCard vc : deck) {
			if (first)
				first = false;
			else
				res.append("\n");

			res.append("\t" + vc.toString());

		}
		return res.toString();
	}

	//PLAYER - CHALLENGE PLAYER
	public boolean challengePlayer(Player opponent) {

		if (opponent == null || opponent == this)
			throw new IllegalArgumentException("Player is challenging null or himself");

		ArrayDeque<VehicleCard> thisPlayedCards = new ArrayDeque<VehicleCard>();
		ArrayDeque<VehicleCard> opponentPlayedCards = new ArrayDeque<VehicleCard>();

		while (true) {

			if (this.deck.size() == 0 || opponent.deck.size() == 0) {
				this.deck.addAll(thisPlayedCards);
				opponent.deck.addAll(opponentPlayedCards);
				return false;
			}

			VehicleCard thisCard = this.playNextCard();
			VehicleCard opCard = opponent.playNextCard();

			if (thisCard.compareTo(opCard) > 0) {
				this.deck.add(thisCard);
				this.deck.add(opCard);
				this.deck.addAll(thisPlayedCards);
				this.deck.addAll(opponentPlayedCards);
				return true;
			}

			if (thisCard.compareTo(opCard) < 0) {
				opponent.deck.add(thisCard);
				opponent.deck.add(opCard);
				opponent.deck.addAll(thisPlayedCards);
				opponent.deck.addAll(opponentPlayedCards);
				return false;
			}

			if (thisCard.compareTo(opCard) == 0) {
				thisPlayedCards.add(thisCard);
				opponentPlayedCards.add(opCard);
			}

		}

	}
	
	//PLAYER - METHODS WHICH RETURN COMPARATORS

	public static Comparator<Player> compareByScore() {
	 return (Player a, Player b) -> {
		 if (a == null || b == null) {
			 throw new IllegalArgumentException("Comparing null player - Player.compareByScore"); //added exception
		 }
		 return (int) Math.signum(a.getScore() - b.getScore()); 
		};
	}

	public static Comparator<Player> compareByDeckSize() {
		return (Player a, Player b) -> {
			 if (a == null || b == null) {
				 throw new IllegalArgumentException("Comparing null player - Player.compareByScore"); //added exception
			 }
			return (int) Math.signum(a.deck.size() - b.deck.size());
		};
		
		
	}
}
