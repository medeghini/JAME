package net.sf.jame.contextfree.parser;

enum EModType {  
	unknown(""), 
	x("\u0095\u00E7\u0048\u005E\u00CC\u0006"), 
	y("\u0084\u002B\u00F3\u00BB\u0093\u0059"), 
	z("\u00C8\u003A\u0012\u0032\u0036\u0071"), 
	transform("\u0088\u0090\u0054\u00C5\u00D3\u0020"), 
	size("\u0064\u00EC\u005B\u004B\u00EE\u002B"), 
	rot("\u0084\u00B0\u0092\u0026\u0059\u00E2"), 
	skew("\u0084\u00B0\u0092\u0026\u0059\u00E3"), 
	flip("\u0043\u005A\u0017\u00EA\u0012\u0005"), 
	zsize("\u0064\u00EC\u005B\u004B\u00EE\u002B"), 
	Entropy(""), 
	hue("\u0002\u00DE\u002B\u002C\u0025\u00A1"), 
	sat("\u0018\u004F\u00CF\u0004\u003F\u00E5"), 
	bright("\u001F\u003F\u00EB\u00A2\u00A2\u007E"), 
	alpha("\u00B4\u00FF\u009E\u0045\u00EE\u007E"), 
	hueTarg("\u00AF\u00E5\u0058\u0033\u0020\u00F8"), 
	satTarg("\u0098\u0080\u00ED\u0044\u002F\u00F2"), 
	brightTarg("\u0068\u00D6\u00CB\u008A\u0096\u0020"), 
	alphaTarg("\u0024\u004C\u00CC\u0041\u0009\u00C7"), 
	targHue("\u00DB\u003F\u00A1\u00DA\u00E7\u0045"), 
	targSat("\u00DA\u0075\u0013\u00D3\u0030\u00EA"), 
	targBright("\u008F\u0001\u002B\u0075\u00C3\u0025"), 
	targAlpha("\u00E7\u00CD\u005E\u00E3\u0088\u00F4"), 
	time("\u0020\u00C6\u00E8\u0002\u00ED\u0027"), 
	timescale("\u0078\u008E\u00C8\u002C\u001C\u0096"), 
	stroke(""), 
	param(""), 
	x1(""), 
	y1(""), 
	x2(""), 
	y2(""), 
	xrad(""), 
	yrad(""), 
	modification("\u0088\u0090\u0054\u00C5\u00D3\u0020"), 
	lastModType("");
	
	private String entropy;
	
	private EModType(String entropy) {
		this.entropy = entropy;
	}

	public static EModType modTypeByOrdinal(int ordinal) {
		for (EModType type : EModType.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return unknown;
	}

	public static EModType modTypeByName(String name) {
		for (EModType type : EModType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return unknown;
	}

	public String getEntropy() {
		return entropy;
	}
}