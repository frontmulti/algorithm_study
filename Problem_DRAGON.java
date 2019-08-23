import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_DRAGON {

	private static int C, N, P, L;
	
	private static final int MAX = 1000000000 + 1;
	private static int[] LENGTH = new int[51]; // length[i] = X나 Y를 i번 치환한 후의 길이
	private static final String EXPAND_X = "X+YF";
	private static final String EXPAND_Y = "FX-Y";
	
	
	public static void main(String[] args) throws Exception {
		precalc();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		C = Integer.parseInt(st.nextToken());
		for (int i = 0; i < C; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			P = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			String result = "";
			for (int j = 0; j < L; j++) {
				result += expand("FX", N, P + j - 1);
			}
			System.out.println(result);
		}
		
	}
	
	private static void curve(String seed, int generations) {
		
		// 기저 사례
		if(generations == 0) {
			System.out.print(seed);
			return;
		}
		
		for(int i = 0; i < seed.length(); i++) {
			if(seed.charAt(i) == 'X') {
				curve("X+YF", generations-1);
			} else if(seed.charAt(i) == 'Y') {
				curve("FX-Y", generations-1);
			} else {
				System.out.print(seed.charAt(i));
			}
		}
	}
	
	private static void precalc() {
		LENGTH[0] = 1;
		for (int i = 1; i <= 50; i++) {
			LENGTH[i] = Math.min(MAX, LENGTH[i-1] * 2 + 2);
		}
	}
	
	private static char expand(String dragonCurve, int generations, int skip) {
		
		if(generations == 0) {
			// TEST : skip < dragonCurve.length())
			return dragonCurve.charAt(skip);
		}
		
		for (int i = 0; i < dragonCurve.length(); i++) {
			// 문자열이 확장되는 경우
			if(dragonCurve.charAt(i) == 'X' || dragonCurve.charAt(i) == 'Y') {
				if(skip >= LENGTH[generations]) {
					skip -= LENGTH[generations];
				} else if(dragonCurve.charAt(i) == 'X') {
					return expand(EXPAND_X, generations-1, skip);
				} else {
					return expand(EXPAND_Y, generations-1, skip);
				}
			} else if(skip > 0) {
				skip-=1;
			} else {
				return dragonCurve.charAt(i);
			}
			
		}
		return '#';
	}

}
