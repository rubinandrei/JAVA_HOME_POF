package homework.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Runner {

    final static List<Class> dirs = new ArrayList<Class>();

    public static void main(String[] args) {


        ArrayList<Integer> arrayList = new ArrayList<>(2);
        arrayList.add(5);
        arrayList.add(6);
        arrayList.add(1,7);
        System.out.print(arrayList.indexOf(6));
        arrayList.remove(1);
        System.out.print(arrayList.indexOf(6));

        Runner test  = new Runner();
        test.getClasses();
        TestRunner.run(dirs);
    }

    private List<Class> getClasses() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = null;
            resources = classLoader.getResources("");
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.toString().contains("test") || resource.toString().contains("main")) {
                    findClasses(new File(resource.getFile()));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dirs;
    }

    private void findClasses(File directory) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findClasses(file);
            }else{
                String packageNameClass = file.getAbsolutePath().contains("main") ?
                        file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("java" + System.getProperty("file.separator")+ "main")+10,file.getAbsolutePath().length()).replace(System.getProperty("file.separator"),"."):
                        file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("java" + System.getProperty("file.separator")+ "test")+10,file.getAbsolutePath().length()).replace(System.getProperty("file.separator"),".");
                packageNameClass = packageNameClass.substring(0, packageNameClass.length() - 6);
                if(!packageNameClass.equals(this.getClass().getName())) {
                    dirs.add(Class.forName(packageNameClass));
                }
            }
        }
    }
}
