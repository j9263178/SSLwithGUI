package server;

public class Parser {
	public static String[] parse(String input) {

		if (input.equals("/EXIT")){
			return new String[]{"EXIT"};
		}

		if(input.substring(0, 1).equals("/")){
			String[] tokens = input.substring(1).split(" ");
			switch (tokens[0]) {
				default:

					return new String[] { "", input };

				case "NICK":

					if(input.substring(6).length() >= 25)
						return new String[] { "NICK", input.substring(6,24) };
					else
						return new String[] { "NICK", input.substring(6,input.length()-1) };

				case "ONLINE":
						return new String[] { "ONLINE" };
			}
		}

		else {
			return new String[] { "", input };
		}
	}
}
