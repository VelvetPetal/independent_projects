import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;

@Setter
@Getter
public class Node {

    private File folder;
    private ArrayList<Node> children;
    private long size;
    private int level;
    private long sizeLimit;

    public Node(File folder, long sizeLimit) {
        this(folder);
        this.sizeLimit = sizeLimit;
    }

    public Node(File folder) {
        this.folder = folder;
        children = new ArrayList<>();
    }

    public void addChild(Node node) {
        children.add(node);
        node.setLevel(level + 1);
    }


    @Override
    public String toString() {

        String size = SizeCalculator.getHumanReadableSize(getSize());
        StringBuilder builder = new StringBuilder();
        builder.append(folder.getName() + " - " + size + "\n");
        for (Node child : children) {
            if (child.getSize() < sizeLimit) {
                continue;
            }
            builder.append("  ".repeat(child.getLevel()) + child.toString());
        }
        return builder.toString();
    }
}
