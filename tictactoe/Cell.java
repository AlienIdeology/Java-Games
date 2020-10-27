public class Cell {
    private int state;
    
    public Cell() {
        state = 0;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public int getState() {
        return state;
    }
    
    public boolean isFilled() {
        return state != 0;
    }
    
    public String toString() {
        switch (state) {
            case 0:
                return " ";
            case 1:
                return "O";
            default: //-1
                return "X";
        }
    }
    
}
