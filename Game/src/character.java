public class character {
	public char[] f(char[] str) {

		if (str != null) {
			char a[] = new char[str.length];
			int ctr2 = 0;
			for (int ctr = str.length - 1; ctr >= 0; --ctr) {
				a[ctr2++] = str[ctr];
			}
			return a;
		}
		return null;

	}
}
