package test;

import java.math.BigInteger;

public class GrammarTest{
	
	/**
	 * @param ss
	 */
	/**
	 * @param ss
	 */
	public  static void main(String [] ss){
		String string = getRelativedPosition(900,11);
		System.out.println(string);
	}
	static int[] arrangement_count={
		1,
		4,
		13,
		40,
		121,
		364,
		1093,
		3280,
		9841,
		29524};
	static int[] arrangement_count_per_level={
		1,
		3,
		9,
		27,
		81,
		243,
		729,
		2187,
		6561,
		19683
	};
	static String getPosition(int number){
		int [] x = getCurrentPosition(number);
		return "level:"+x[0]+"position:"+x[1];
	}
	static int[] getCurrentPosition(int number){
		int level=0;
		for(; level<arrangement_count.length;level++){
			if(arrangement_count[level]<number){
				continue;
			}
			if(arrangement_count[level]>=number){
				break;
			}
		}
		int position=0;
		if(level>0){
			position=number-arrangement_count[level-1];
		}else{
			position=1;
		}

if(isDebug)System.out.println("level:"+level+" position:"+position);
		return new int[]{level,position};
	}
	/**
	 * @param number 所有的会员计数
	 * @param node 计算当前node
	 */
	static boolean isDebug=false;
	static String getRelativedPosition(int number,int node){
		String result="";
		int current[] = getCurrentPosition(number);
		int nnn[] = getCurrentPosition(node);
		int relativedLevel=0;
		for(int i = nnn[0];i<=current[0];i++){
			if(isDebug)System.out.print("level:"+i+" | ");
			result=result+"L:"+(++relativedLevel)+" | ";
			int relativedCount=0;
			for(int j=0; j < (int)Math.pow(3,i-nnn[0]) ;j++){
				if(i==0){
					if(isDebug)System.out.print(node+j+" ");
					relativedCount++;
				}else{
					int position=arrangement_count[i-1]+
							(int)Math.pow(3,i-nnn[0])*(nnn[1]-1)+
							+j+1;
					if ((int)Math.pow(3,i-nnn[0])*(nnn[1]-1)+j+1<=current[1]){
						if(isDebug)System.out.print(position+" ");
						relativedCount++;
					}else{
						if(isDebug)System.out.print(position+"(still empty)");
					}
				}
			}
			if(isDebug)System.out.println(" <<" );
			result+=relativedCount+"\n";
		}
		return result;
	}

}
	