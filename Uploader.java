import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
/**
This class is used to keep track of which nodes are adjacent.
*/
class ColEdge
{
  int u;
  int v;
}

public class Uploader
{
  public final static boolean DEBUG = false;
  public final static String COMMENT = "//";
  /**
  This method is used to get a graph from a file. It shows a file dialog and asks the user to
  select a .txt file containing informations about the graph. If the graph is too big it shows an alert
  containing a warning.
  */
  public static Graph getGraphFromFile() {
    JFileChooser chooser = new JFileChooser(".");
    int fc = chooser.showOpenDialog(null);
    String file = null;
    try {
      file =  chooser.getSelectedFile().getAbsolutePath();
    }
    catch (NullPointerException error) {
      System.out.println("Did not choose a file!");
    }
    if(file != null) {
      if(fc == JFileChooser.APPROVE_OPTION) {
        System.out.println("You chose: " + file);
      }
      String inputfile = file;
      boolean seen[] = null;
      //! n is the number of vertices in the graph
      int n = -1;
      //! m is the number of edges in the graph
      int m = -1;
      //! e will contain the edges of the graph
      ColEdge e[] = null;
      try 	{
        FileReader fr = new FileReader(inputfile);
        BufferedReader br = new BufferedReader(fr);
        String record = new String();
        //! The first few lines of the file are allowed to be comments, staring with a // symbol.
        //! These comments are only allowed at the top of the file.
        //! -------------------------------------------------------------------------------------
        while ((record = br.readLine()) != null)
        {
          if( record.startsWith("//") ) continue;
          break; // Saw a line that did not start with a comment -- time to start reading the data in!
        }
        if( record.startsWith("VERTICES = ") )
        {
          n = Integer.parseInt( record.substring(11) );
          if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
        }
        seen = new boolean[n+1];
        record = br.readLine();
        if( record.startsWith("EDGES = ") )
        {
          m = Integer.parseInt( record.substring(8) );
          if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
        }
        e = new ColEdge[m];
        for( int d=0; d<m; d++)
        {
          if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
          record = br.readLine();
          String data[] = record.split(" ");
          if( data.length != 2 )
          {
            System.out.println("Error! Malformed edge line: "+record);
            System.exit(0);
          }
          e[d] = new ColEdge();
          e[d].u = Integer.parseInt(data[0]);
          e[d].v = Integer.parseInt(data[1]);
          seen[ e[d].u ] = true;
          seen[ e[d].v ] = true;
          if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
        }
        String surplus = br.readLine();
        if( surplus != null )
        {
          if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
        }
      }
      catch (IOException ex)
      {
        // catch possible io errors from readLine()
        //!------------------------------------------------------------------------------------------------------------------------
        System.out.println("Error! Problem reading file "+inputfile);
        System.exit(0);
      }
      for( int x=1; x<=n; x++ )
      {
        if( seen[x] == false )
        {
          if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
        }
      }
      //! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
      //! e[1] will be the second edge...
      //! (and so on)
      //! e[m-1] will be the last edge
      //!
      //! there will be n vertices in the graph, numbered 1 to n
      if(n > 100) {
        return null;
      }
      Graph g = new Graph(e, n, m);
      System.out.println(e.length);
      //!Return the graph 
      //!----------------------------------------------------------------------------------------------------------
      return g;
    }
    return null;
  }
}
