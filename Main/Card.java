package Main;

import Geometry.Quad;

public class Card extends Quad {
	byte type;

	public Card() {
		super();
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

}
