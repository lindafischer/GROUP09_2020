import java.util.ArrayList;
import java.util.LinkedList;

class ColEdge
{
	int u;
	int v;
	
	public ColEdge(int u, int v) {
		this.u = u;
		this.v = v;
	}
		
	public ColEdge() {
		
	}
	
}


public class CycleCheck {



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		

		/**
		edges[0] = new ColEdge(1,2);
		edges[1] = new ColEdge(1,3);
		edges[2] = new ColEdge(1,4);
		edges[3] = new ColEdge(1,5);
		edges[4] = new ColEdge(1,6);
		edges[5] = new ColEdge(1,7);
		edges[6] = new ColEdge(2,3);
		edges[7] = new ColEdge(2,4);
		edges[8] = new ColEdge(2,5);
		edges[9] = new ColEdge(3,4);
		edges[10] = new ColEdge(3,7);
		edges[11] = new ColEdge(5,6);
		edges[12] = new ColEdge(6,7);
		 **/

		
		ColEdge[] edges = new ColEdge[15];
		edges[11] = new ColEdge(1,2);
		edges[1] = new ColEdge(1,3);
		edges[2] = new ColEdge(1,4);
		edges[5] = new ColEdge(1,5);
		edges[4] = new ColEdge(1,6);
		edges[3] = new ColEdge(1,7);
		edges[6] = new ColEdge(2,3);
		edges[9] = new ColEdge(2,4);
		edges[8] = new ColEdge(2,5);
		edges[7] = new ColEdge(3,4);
		edges[10] = new ColEdge(3,7);
		edges[0] = new ColEdge(5,6);
		edges[12] = new ColEdge(6,7);
		
		edges[13] = new ColEdge(7,8);
		edges[14] = new ColEdge(6,8);
		
		
		/**edges = new ColEdge[5];
		edges[0] = new ColEdge(1,2);
		edges[1] = new ColEdge(1,7);
		edges[2] = new ColEdge(3,1);
		edges[3] = new ColEdge(7,8);
		edges[4] = new ColEdge(1,8);**/
		
		/**edges = bubbleSortEdges(flipFirstToLow(edges));
		for(int x=0; x<edges.length; x++) {
			System.out.println(edges[x].u+" - "+edges[x].v);
		}
		System.out.println("\n==================\n");**/

		System.out.println("Anzahl Knoten: "+getNumberOfVertices(edges));
		
		if (isThereAtLeastOneTriangle(edges)) {
			System.out.println("Mindestens ein Dreieck enthalten");
		} else {
			System.out.println("Kein Dreieck enthalten");
		}
		System.out.println("Anzahl der Dreiecke: "+getNumberOfTriangles(edges));
		System.out.println("Lower Bounds (nach Dreieckserkennung): "+getLowerBoundsByTriangleDetection(edges));


		System.out.println("Done");
	}

	
	 public static int getNumberOfVertices(ColEdge[] edges) {
		ArrayList<Integer> vertices = new ArrayList<>();
		
		for(int i=0; i < edges.length; i++) {
			if (!vertices.contains(edges[i].u)) vertices.add(edges[i].u);
			if (!vertices.contains(edges[i].v)) vertices.add(edges[i].v);
		}
		
		
		return vertices.size();
	}
	
	
	public static int getLowerBoundsByTriangleDetection(ColEdge[] edges) {
		int lowerBounds = 2;
		
		/**
		// old version, not optimized
		if (edges.length == 0) {
			lowerBounds = 1;
		} else if (getNumberOfTriangles(edges)>=1) {
			lowerBounds = 3;
		}**/
		
		//new version: optimized, since we only check if there is at least one triangle in the graph
		if (edges.length == 0) {
			lowerBounds = 1;
		} else if (isThereAtLeastOneTriangle(edges)) {
			lowerBounds = 3;
		}
		
		return lowerBounds;
	}
	
	public static boolean isThereAtLeastOneTriangle(ColEdge[] edg) {
		boolean triangleFound = false;
		
		ColEdge[] edges = new ColEdge[edg.length];
		edges = bubbleSortEdges(flipFirstToLow(edg));
		
		for(int i=0;i<edges.length-1 && !triangleFound;i++) {

			for(int j=i+1; j<edges.length && !triangleFound; j++) {
				ColEdge e1 = edges[i];
				ColEdge e2 = edges[j];

				if (e1.u == e2.u) {
					if (isInEdges(e1.v, e2.v, edges)) {
						//System.out.println(e1.u+"-"+e1.v+"-"+e2.v);
						triangleFound = true;
					}
				}

			}

		}
		
		return triangleFound;
	}
	
	public static int getNumberOfTriangles(ColEdge[] edg) {
		int counter=0;

		ColEdge[] edges = new ColEdge[edg.length];
		edges = bubbleSortEdges(flipFirstToLow(edg));
		
		for(int i=0;i<edges.length-1;i++) {

			for(int j=i+1; j<edges.length; j++) {
				ColEdge e1 = edges[i];
				ColEdge e2 = edges[j];

				if (e1.u == e2.u) {
					if (isInEdges(e1.v, e2.v, edges)) {
						//System.out.println(e1.u+"-"+e1.v+"-"+e2.v);
						counter++;
					}
				}

			}

		}

		return counter;
	}

	// 
	public static boolean isInEdges(int u, int v, ColEdge[] edgs) {
		boolean ret = false;
		int i=0;

		while (!ret && i<edgs.length) {
			if ((edgs[i].u==u && edgs[i].v==v) || (edgs[i].v==u && edgs[i].u==v) ) ret=true;
			i++;
		}



		return ret;
	}

	public static ColEdge[] flipFirstToLow(ColEdge[] e) {
		for(int i = 0; i<e.length; i++) {
			if (e[i].v<e[i].u) {
				int tmp = e[i].u;
				e[i].u = e[i].v;
				e[i].v = tmp;
			}
		}
		
		return e;
	}
	
	
	public static ColEdge[] bubbleSortEdges(ColEdge[] e) {
		for(int i=0;i<e.length; i++) {
			for(int j=i+1; j<e.length; j++) {
				if (e[i].u>e[j].u) {
					ColEdge tmpE = new ColEdge();
					tmpE.u = e[i].u;
					tmpE.v = e[i].v;

					e[i].v = e[j].v;
					e[i].u = e[j].u;

					e[j].u = tmpE.u;
					e[j].v = tmpE.v;

				} else if (e[i].u==e[j].u) {

					if (e[i].v>e[j].v) {
						ColEdge tmpE = new ColEdge();
						tmpE.u = e[i].u;
						tmpE.v = e[i].v;

						e[i].v = e[j].v;
						e[i].u = e[j].u;

						e[j].u = tmpE.u;
						e[j].v = tmpE.v;

					} 

				}

			}

		}

		return e;
	}

}
