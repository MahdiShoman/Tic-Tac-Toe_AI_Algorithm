public class BestMove {

    int i = -1;
    int j = -1;
    int win =0;
    int lose =0;
    int draw=0;

    public BestMove(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "BestMove{" +
                "i=" + i +
                ", j=" + j +
                ", win=" + win +
                ", lose=" + lose +
                ", draw=" + draw +
                '}';
    }
}
