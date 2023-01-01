import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        ParametersBag bag = new ParametersBag(args);
        String path = bag.getPath();
        long sizeLimit = bag.getLimit();
        File file = new File(path);
        long start = System.currentTimeMillis();
        Node root = new Node(file, sizeLimit);
        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(calculator);
        System.out.println(root);
        System.out.println((double) (System.currentTimeMillis() - start) / 1000 + " sec");
    }
}
