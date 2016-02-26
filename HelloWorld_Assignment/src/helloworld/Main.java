package helloworld;

import helloworld.fileManager.ExplorerRunner;
import helloworld.fileManager.FileExplorer;

public class Main {
    public static void main(String args[]) {
        new ExplorerRunner().run(new FileExplorer(),550,300);
    }
}
