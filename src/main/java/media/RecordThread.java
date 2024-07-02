package media;

public class RecordThread implements Runnable{
    private Record r;
    public RecordThread(Record record){
        this.r = record;
    }
    @Override
    public void run() {
        r.start();
    }
}
