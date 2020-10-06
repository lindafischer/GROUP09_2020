public static int graphColour(int k, int [] x, int [][] G, int n){
		int ChromaticNumber = 0;
		for(int c = 1; c<=n; c++){
			if(isSafe(k,c,G,x,n)){
				x[k] = c;
				if((k+1)<n){
					ChromaticNumber = graphColour(k+1, x, G, n);
				}
				else {
					ChromaticNumber = max(x);
				}
				
			}
		}
		return ChromaticNumber;
	}
	
	public static boolean isSafe(int k, int c, int [][] G, int [] x, int n){
		for(int i = 0; i< n; i++){
			if(G[k][i] == 1 && c == x[i]){
				return false;
			}
		}
		return true;
	}
	
	public static int max (int [] x) {
		int ChromaticNumber = x[0];
		for (int i = 0; i < x.length; i++) { 
            if (x[i] < ChromaticNumber) 
                ChromaticNumber = x[i]; 
		}
        return ChromaticNumber;  
	}
		
}
