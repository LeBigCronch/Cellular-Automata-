
import java.util.Arrays;

public class CellularAutomata {
    static char[][] array = new char[40][40]; // change these numbers to change the size of the grid. needs to be the same otherwise things break 

    public static void main(String[] args) throws InterruptedException {

        for(int i = 0; i < array.length; i++){
            Arrays.fill(array[i], '.'); // sets every space as a . because space does funky things for some reason 
        }
	

        array[31][22] = '@'; // i know this is a horrible way to put in cells to grow from but im too lazy to make it any better 
        
        render(0);
        Thread.sleep(500);
        evalulate();
    }

    static void evalulate() throws InterruptedException{
        int x = 0;
        int y = 0;
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;

        for(int n = 0; n < 200; n++){ //you can change the max number of generations here

	    render(n);
	    convert();
            Thread.sleep(200); // changes this number to slow down or speed up the growing 
            for(int i = 0; i < array.length; i++){
                for(int f = 0; f < array.length; f++){
                    if(array[i][f] == '@'){
                        x = f;
                        y = i;
                        if(f > 0 && array[i][f-1] != '@')		{left = true;}

                        if(f < array.length-1 && array[i][f+1] != '@')  {right = true;}

                        if(i > 0 && array[i-1][f] != '@')		{up = true;}
		
                        if(i < array.length-1 && array[i+1][f] != '@')	{down = true;}

                        grow(up, down, left, right, x ,y, n);
                        up = false;
                        down = false;
                        right = false;
                        left = false;
                    }
                }
            }
        }
    }

    static int grow(boolean up, boolean down, boolean left, boolean right, int x, int y, int n){
        boolean go = true;
        int d = 0; // d is the number of directions it can grow in, up, down, left, or right 

        if(!up){d++;}
        if(!down){d++;}
        if(!left){d++;}
        if(!right){d++;}

        if(d == 2 || d == 3){array[y][x] = '.'; go = false; return 0;} // main rule  
	
        if(go && right){
            if(x+1 > array.length-1){
                x=0;
            }
            array[y][x+1] = '-'; // these are set to a different character than the cell so that each cell grows at the same time and doesn't just go from one point and go down
        }

        if(go && left){
            if(x-1 < 1){
                x = array.length-1;
            }
            array[y][x-1] = '-';
        }

        if(go && up){
            if(y-1 < 1){
                y = array.length-1;
            }
            array[y-1][x] = '-';
        }

        if(go && down){
            if(y+1 > array.length-1){
                y = 0;
            }
            array[y+1][x] = '-';
        }
        
        go = true;
        return 0;
    } 

    static void convert(){
	for(int i = 0; i < array.length; i++){
		for(int f = 0; f < array.length; f++){
			if(array[i][f] == '-'){
				array[i][f] = '@'; // any character marked - is turned into a cell after all the growing has been calculated 
			}	
		}	
	}
    }

    static void render(int n){
        System.out.print("\033[H\033[2J"); 
        for(int i = 0; i < array.length; i++){
            System.out.println( Arrays.toString(array[i]));
        }
        System.out.print("generation " + n);
    }
}
