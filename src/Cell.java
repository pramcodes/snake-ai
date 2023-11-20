class Cell {
    int x, y, distance , dir;
    Cell Parent;
    
    Cell(){
    	
    }
    
    Cell(int x, int y, int distance , Cell par ,int dir ) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        Parent = par;
        this.dir = dir;
    }
}
